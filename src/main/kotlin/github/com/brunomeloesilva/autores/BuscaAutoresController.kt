package github.com.brunomeloesilva.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/autores") // Mudei para /autorex devido a classe BuscaDetalheDeAutoresController
class BuscaAutoresController(val autorRepository: AutorRepository) {

    @Get
    //@Transactional se colocar dar pau.
    // Como @QueryValue não tem valor default, é obrigatório.
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any>{
        if (email.isBlank()){
            val autores = autorRepository.findAll()
            val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }
            return HttpResponse.ok(resposta)
        }

        //val possivelAutor = autorRepository.findByEmail(email)
        val possivelAutor = autorRepository.buscaPorEmail(email)

        if(possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}