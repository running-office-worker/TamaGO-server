# AGENTS.md

This file provides guidance to Codex (Codex.ai/code) when working with code in this repository.

## Project Overview

TamaGo Server is a Spring Boot multi-module Kotlin backend for a running/fitness app with virtual monster companions. Users track running activities, collect monsters, and receive letters.

## Tech Stack

- **Language:** Kotlin 1.9.25, Java 21 (Temurin)
- **Framework:** Spring Boot 3.5.8, Spring Modulith 1.4.3
- **Build:** Gradle (Kotlin DSL)
- **Database:** MySQL with Flyway migrations
- **ORM:** Spring Data JPA + Kotlin JDSL for type-safe queries
- **Auth:** JWT (jjwt), OAuth2 via Kakao/Apple (OpenFeign)
- **Linting:** ktlint (via `org.jlleitschuh.gradle.ktlint` plugin)
- **Notifications:** Firebase Cloud Messaging

## Build & Development Commands

```bash
./gradlew build                              # Full build with tests
./gradlew build -x test                      # Build without tests
./gradlew :tamago-core:test                  # Run tests for core module only
./gradlew :tamago-gateway:bootRun            # Run the application
./gradlew :tamago-gateway:bootJar            # Build deployable JAR
./gradlew ktlintCheck                        # Lint check
./gradlew ktlintFormat                       # Auto-fix lint issues
```

A pre-commit hook runs ktlint check automatically (installed via `installGitHooks` Gradle task on first compile).

## Multi-Module Structure

```
tamago-core          → Business logic, domain models, persistence (library JAR)
tamago-gateway       → REST API, security, controllers (bootable JAR, entry point)
tamago-oauth         → Kakao/Apple OAuth integration via Feign clients
tamago-notification  → Firebase notification service
```

**Module dependencies:** gateway → core, oauth. The core module's `bootJar` is disabled; only gateway produces a runnable artifact.

**Entry point:** `tamago.server.gateway.TamaGoApplicationKt`

## Architecture (Hexagonal + Spring Modulith)

### Core Module Internal Structure

Each domain module in `tamago-core` follows this layout:

```
module/
├── ModuleInfo.kt                    # @ApplicationModule with allowedDependencies
├── {Name}CommandUseCase.kt          # Write operation interface (public API)
├── {Name}QueryUseCase.kt           # Read operation interface (public API)
├── {Name}Facade.kt                 # Orchestration layer (used by controllers)
├── application/
│   ├── service/                    # UseCase implementations
│   ├── validator/                  # Business rule validation
│   └── exception/                  # Module-specific exceptions
├── domain/
│   ├── aggregate/                  # Domain models (pure Kotlin, no JPA annotations)
│   ├── vo/                         # Value objects
│   ├── enum/                       # Domain enums
│   └── port/
│       ├── inbound/                # Command/Query DTOs
│       └── outbound/               # Persistence port interfaces
└── infrastructure/
    ├── entity/                     # JPA entities (mapped to domain via Mapper)
    ├── repository/                 # Persistence adapters implementing ports
    └── mapper/                     # Domain ↔ Entity bidirectional mappers
```

### Key Patterns

- **Domain models are separate from JPA entities.** Mappers convert between them. Domain aggregates have no persistence annotations.
- **Facade pattern:** Facades depend directly on Services within the same domain, but depend on UseCase interfaces for cross-domain access. Controllers always depend on UseCase interfaces, never on Services directly.
- **Commands never query:** CommandUseCase/Service must not perform read operations. When a command needs data, the Facade queries first and passes the resulting object into the command.
- **Port/Adapter:** Persistence ports are interfaces in `domain/port/outbound/`; adapters in `infrastructure/repository/` implement them.
- **CQRS-lite:** Separate `CommandUseCase` (writes) and `QueryUseCase` (reads) interfaces per module.
- **Spring Modulith:** Each module declares `@ApplicationModule(allowedDependencies = [...])`. Only `common` is universally allowed. The `user` module may also depend on `refreshtoken`. Module boundaries are verified by `CoreModularityTests`.
- **Soft delete:** All entities extend `BaseTimeEntity` with `createdAt`, `updatedAt`, `deletedAt`. No hard deletes.
- **No foreign keys in DB:** Referential integrity is managed in application code (see V6 migration).
- **Value objects:** `UserId` is a Kotlin `@JvmInline value class` for type-safe IDs.

### Gateway Module

- Controllers have Swagger annotations (`@Tag`, `@Operation`) directly on the class and methods
- `@CurrentUser` annotation resolves the authenticated `User` from JWT via `UserIdResolver`
- `CustomResponse<T>` wraps all API responses with `requestId`, `status`, `message`, `code`, `data`
- `CustomResponseStatusAspect` sets HTTP status from `CustomResponse.status`
- `RequestIdFilter` generates UUID per request, stored in MDC

### Exception Handling

- Domain exceptions extend `BusinessException(ExceptionCode)`
- Each module defines its own `ExceptionCode` enum implementing the `ExceptionCode` interface
- `ResponseExceptionHandler` (`@RestControllerAdvice`) maps exceptions to `CustomResponse`

## Database

- **MySQL** with `hibernate.ddl-auto: validate` (schema managed by Flyway only)
- **Migrations** in `tamago-core/src/main/resources/db/migration/` (naming: `V{N}__{description}.sql`)
- **Seed data** in `tamago-core/src/main/resources/db/seed/`
- **Config profiles:** `local`, `dev`, `prod` — set via `SPRING_PROFILES_ACTIVE`

## Code Style

- ktlint with Kotlin official style, 4-space indent, 120 char max line length
- Wildcard imports allowed (star import threshold: 5 for top-level, 3 for members)
- Trailing commas allowed
- Test files have no line length limit

## Working Rules

- **Always `git add` new files:** You MUST run `git add <file_path>` immediately after creating every new file, without exception. Do not batch or defer — add each file right after it is written.
- **Never modify existing Flyway migrations:** Never edit previously created migration files (V1–VN). Always create a new version file for any schema changes.
- **Proceed with code changes without asking:** Unless explicitly told otherwise, implement code changes directly without requesting confirmation first.

## Git Workflow

- **Main branch for PRs:** `develop`
- **CI:** Runs `build` on all PRs (GitHub Actions)
- **CD:** Push to `develop` triggers Docker build and deployment to dev server
