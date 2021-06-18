package github.com.brunomeloesilva.autores

import javax.persistence.Embeddable

@Embeddable
class Endereco(enderecoResponse: EnderecoResponse) {
    val rua = enderecoResponse.logradouro
    val cidade = enderecoResponse.localidade
    val estado = enderecoResponse.uf
}
