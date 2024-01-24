package br.com.alura.screenmatchspring.service;

public interface IConverteDados {

    // Método para obter dados usando Generics
   <T> T obterDados(String json, Class<T> classe);
}
