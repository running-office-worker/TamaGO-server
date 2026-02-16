package tamago.server.core.common.jdsl

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import jakarta.persistence.EntityManager

@Suppress("SqlSourceToSinkFlow")
inline fun <reified T : Any> EntityManager.findAll(
    query: JpqlQuery<*>,
    context: JpqlRenderContext,
): List<T> {
    val rendered = JpqlRenderer().render(query, context)
    return createQuery(rendered.query, T::class.java)
        .apply { rendered.params.forEach { (key, value) -> setParameter(key, value) } }
        .resultList
}

@Suppress("SqlSourceToSinkFlow")
inline fun <reified T : Any> EntityManager.findOne(
    query: JpqlQuery<*>,
    context: JpqlRenderContext,
): T? {
    val rendered = JpqlRenderer().render(query, context)
    return createQuery(rendered.query, T::class.java)
        .apply { rendered.params.forEach { (key, value) -> setParameter(key, value) } }
        .resultList
        .firstOrNull()
}
