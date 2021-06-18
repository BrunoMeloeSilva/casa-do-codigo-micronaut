package github.com.brunomeloesilva.autores

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(rollback = false, /*Padrao é true, sendo rollback para @Test*/
               transactionMode = TransactionMode.SINGLE_TRANSACTION, //Esse é o padrão TransactionMode.SEPARATE_TRANSACTIONS que faz @BeforeEach, tenha seu commit separado do @Test
                transactional = false //Cada operacao é autocommit por padrão
)
class CarroTest{
    @Inject
    lateinit var carroRepository: CarroRepository

    @BeforeEach
    fun antesDosTestes(){
        carroRepository.deleteAll()
    }

    @AfterEach
    fun depoisDosTestes(){
        carroRepository.deleteAll()
    }

    @Test
    fun `deve inserir um novo carro`(){
        carroRepository.save(Carro("Gol", "HPX1234"))
        assertEquals(1, carroRepository.count())
    }

    @Test
    fun `deve encontrar carro por placa`(){
        //carroRepository.deleteAll()
        carroRepository.save(Carro("Palio", "HPX1235"))
        val encontrado = carroRepository.existsByPlaca("HPX1235")
        assertTrue(encontrado)
    }
}