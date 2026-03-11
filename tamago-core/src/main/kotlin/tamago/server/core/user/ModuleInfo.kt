package tamago.server.core.user

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(allowedDependencies = ["common", "refreshtoken"])
@PackageInfo
class UserModuleInfo
