package github.com.brunomeloesilva.autores

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import io.micronaut.xml.jackson.server.convert.XmlMapperFactory
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastrarAutorController(val autorRepository: AutorRepository,
                               val enderecoClient: EnderecoClient) {
    @Post
    fun cadastrar(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any> {
        println("Request: $request")

        val enderecoResponse = enderecoClient.consultaJson(request.cep)

        val autor = request.paraAutor(enderecoResponse.body()!!)

        autorRepository.save(autor)
        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }
}

