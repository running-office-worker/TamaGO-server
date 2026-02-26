package tamago.server.gateway.common.resolver

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import tamago.server.core.common.vo.UserId
import tamago.server.core.user.UserQueryUseCase
import tamago.server.gateway.common.annotation.CurrentUser

@Component
class UserIdResolver(
    private val userQueryUseCase: UserQueryUseCase,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(CurrentUser::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? =
        userQueryUseCase.get(
            UserId(
                SecurityContextHolder
                    .getContext()
                    .authentication.name
                    .toLong(),
            ),
        )
}
