package br.com.alura.screenmatchspring.service;

import br.com.alura.screenmatchspring.model.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    // Atributos da Classe
    /// Mapper - Objeto Jackson que faz a conversão.
    private ObjectMapper mapper = new ObjectMapper();

    // Método para obter dados
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe); // Lê o json e transforma na classe indicada (no caso, generics)
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
