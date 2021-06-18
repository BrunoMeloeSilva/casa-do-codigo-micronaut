package github.com.brunomeloesilva.autores

class DetalhesDoAutorResponse(autor: Autor) {
    val nome = autor.nome
    val email = autor.email
    val descricao = autor.descricao
}
