package github.com.brunomeloesilva.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Controller("/autores/{id}")
open class AtualizaAutorController(val autorRepository: AutorRepository) {
    @Put
    @Transactional
    open fun cadastrar(@PathVariable id: Long, descricao: String) : HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        autor.descricao = descricao
        //autorRepository.update(autor)
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}