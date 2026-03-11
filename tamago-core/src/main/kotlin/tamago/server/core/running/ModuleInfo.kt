package tamago.server.core.running

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(allowedDependencies = ["common", "monster", "monster :: aggregate", "monster :: vo"])
@PackageInfo
class RunningModuleInfo
