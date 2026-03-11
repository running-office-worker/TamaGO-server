package tamago.server.core.letter

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(allowedDependencies = ["running", "notification"])
@PackageInfo
class LetterModuleInfo
