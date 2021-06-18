package github.com.brunomeloesilva.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject


@MicronautTest
internal class CadastrarAutorControllerTest{

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve cadastrar um novo autor`() {

        val novoAutorRequest = NovoAutorRequest("Bruno Silva",
            "brunosilva@gmail.com",
            "Bla bla bla",
            "69000000")
        val enderecoResponse = EnderecoResponse("01001-000",
            "Praça da Sé",
            "lado ímpar",
            "Sé",
            "São Paulo",
            "SP",
            "3550308",
            "1004",
            "11",
            "7107")

        Mockito.`when`(enderecoClient.consultaJson(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autores", novoAutorRequest)
        val response = client.toBlocking().exchange(request, Any::class.java)
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location").matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}