package github.com.brunomeloesilva.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Controller("/autores/{id}")
class DeletarAutorController(val autorRepository: AutorRepository) {
    @Delete
    fun cadastrar(@PathVariable id: Long) : HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        //autorRepository.delete(possivelAutor.get())
        autorRepository.deleteById(id)
        return HttpResponse.ok()
    }
}