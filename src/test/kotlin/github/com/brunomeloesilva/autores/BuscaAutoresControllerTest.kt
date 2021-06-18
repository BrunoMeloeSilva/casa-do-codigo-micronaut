package github.com.brunomeloesilva.autores

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest{

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setup() {
        val endereco = Endereco(EnderecoResponse("01001-000",
            "Praça da Sé",
            "lado ímpar",
            "Sé",
            "São Paulo",
            "SP",
            "3550308",
            "1004",
            "11",
            "7107"))
        autor = Autor("Zupper 01", "zupper1@zup.com.br", "Mestre das documentações.", endereco)

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown(){
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {

        val response = client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesDoAutorResponse::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.email, response.body()!!.email)
        assertEquals(autor.descricao, response.body()!!.descricao)
    }
}