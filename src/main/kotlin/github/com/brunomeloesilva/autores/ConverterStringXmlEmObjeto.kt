package github.com.brunomeloesilva.autores

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

internal val kotlinXmlMapper = XmlMapper(JacksonXmlModule().apply {
    setDefaultUseWrapper(false)
}).registerKotlinModule()
    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)


internal inline fun <reified T : Any> parseAs(stringXML: String): T {
    return kotlinXmlMapper.readValue(stringXML)
}

fun main(args: Array<String>) {
    val xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<xmlcep>\n" +
            "  <cep>01001-000</cep>\n" +
            "  <logradouro>Praça da Sé</logradouro>\n" +
            "  <complemento>lado ímpar</complemento>\n" +
            "  <bairro>Sé</bairro>\n" +
            "  <localidade>São Paulo</localidade>\n" +
            "  <uf>SP</uf>\n" +
            "  <ibge>3550308</ibge>\n" +
            "  <gia>1004</gia>\n" +
            "  <ddd>11</ddd>\n" +
            "  <siafi>7107</siafi>\n" +
            "</xmlcep>"
    val obj = parseAs<EnderecoResponseTeste>(xml)

    println(obj)
}