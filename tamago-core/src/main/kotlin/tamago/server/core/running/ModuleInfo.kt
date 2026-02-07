package tamago.server.core.running

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(allowedDependencies = ["common", "monster"])
@PackageInfo
class RunningModuleInfo
