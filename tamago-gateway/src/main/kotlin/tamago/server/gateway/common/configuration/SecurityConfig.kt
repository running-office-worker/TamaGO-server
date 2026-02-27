package tamago.server.gateway.common.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tamago.server.gateway.common.filter.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        disabledConfigurations(httpSecurity)
        configurationSessionManagement(httpSecurity)
        configurationCors(httpSecurity)
        configureAuthorizeHttpRequests(httpSecurity)
        configureJwtFilter(httpSecurity)
        return httpSecurity.build()
    }

    private fun configureJwtFilter(httpSecurity: HttpSecurity) {
        httpSecurity
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    private fun disabledConfigurations(httpSecurity: HttpSecurity) {
        httpSecurity
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
    }

    private fun configurationSessionManagement(httpSecurity: HttpSecurity) {
        httpSecurity
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
    }

    private fun configurationCors(httpSecurity: HttpSecurity) {
        httpSecurity
            .cors { }
    }

    private fun configureAuthorizeHttpRequests(httpSecurity: HttpSecurity) {
        httpSecurity
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(*SWAGGER_PATTERNS)
                    .permitAll()
                    .requestMatchers(*STATIC_RESOURCES_PATTERNS)
                    .permitAll()
                    .requestMatchers(*PERMIT_ALL_PATTERNS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
    }

    companion object {
        private val SWAGGER_PATTERNS =
            arrayOf(
                "/swagger-ui/**",
                "/actuator/**",
                "/v3/api-docs/**",
            )
        private val STATIC_RESOURCES_PATTERNS =
            arrayOf(
                "/images/**",
                "/css/**",
                "/js/**",
                "/static/**",
            )
        private val PERMIT_ALL_PATTERNS =
            arrayOf(
                "/login/kakao",
                "/v1/auth/social-login/kakao",
                "/api/v1/auth/**",
                "/error",
                "/api/v1/monsters/assets",
                "/api/v1/monsters/assets/check-updates",
            )
    }
}
