package tamago.server.core.running

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(allowedDependencies = ["common"])
@PackageInfo
class RunningModuleInfo
