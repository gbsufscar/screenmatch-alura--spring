package br.com.alura.screenmatchspring.principal;

import br.com.alura.screenmatchspring.model.DadosEpisodios;
import br.com.alura.screenmatchspring.model.DadosSerie;
import br.com.alura.screenmatchspring.model.DadosTemporada;
import br.com.alura.screenmatchspring.service.ConsumoApi;
import br.com.alura.screenmatchspring.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    // Atributos
    private Scanner leitura = new Scanner(System.in); // Atributo do tipo scanner para receber do teclado do usuário.

    // Atributos - Declarar as constantes para os endereço de consulta (no site OMDAPI)
    private final String ENDERECO = "https://omdbapi.com/?t="; // Constante - usar a palavra-chave final
    private final String API_KEY = "&apikey=690913ef"; // Constante - Nomenclarura: Maiúscula com snake-case.

    // Atributos que são utilizdos em todas as consultas
    /// Guardar e instanciar a Classe ConsumoApi
    private ConsumoApi consumo = new ConsumoApi();

    // Atributos do conversor de dados.
    private ConverteDados conversor = new ConverteDados();


    // Método para exibir o menu para o usuário
    public void exibeMenu() {
        // Entrada de dados do usuário
        System.out.println("Digite o nome da Série para busca:");

        // Declarar a variável para receber a busca digitada no terminal.
        var nomeSerie = leitura.nextLine(); // Pegar a string digitada no teclado/terminal.

        // Chamar método - Consulta API para obter o json usando a biblioteca GSON
        String endereco = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        String json = consumo.obterDados(endereco); // Arg - endereço com a busca

        // Conversor de json para classe.
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class); // Deseja converter o json em DadosSerie.

        // Exibir os dados da consulta convertido no formato da classe desejado
        /// Mostra representando na forma do toString do Record
        System.out.println("SÉRIE - Informações convertidas no formato desejado: \n" + dados);
        System.out.println("******** \n");

        // Busca de Informações de uma Temporada
        /// Usar um laço for para iterar sobre o total de temporadas
        List<DadosTemporada> temporadas = new ArrayList<>(); // Instancia a lista do tipo Dados Temporada
        for (int i = 1; i <= dados.totalTemporadas(); i++) { // dados foi delcarado na busca de série
            /// Obter o json da consulta
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")
                    + "&season=" + i + API_KEY);

            /// Instanciar Dados Temporada, converter e transformar o json
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);

            /// Adicionar as temporadas na lista de temporadas
            temporadas.add(dadosTemporada);
        }

        // Exibir os dados da consulta convertido no formato desejado
        System.out.println("TEMPORADAS - Informações convertidas no formato desejado:");
        temporadas.forEach(System.out::println);
        System.out.println("******** \n");

        /*
        CÓDIGO QUE SERÁ SUBSTITUÍDO POR UMA FUNÇÃO LAMBDA.
        // Exibir os títulos da série por temporadas
        for (int i = 0; i < dados.totalTemporadas(); i++) {
            // Lista de Episódios da Temporada
            System.out.println();
            System.out.println("Série " + nomeSerie.toUpperCase() + ", Temporada nº " + (i+1));
            List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios(); // Pegar os episódios das temporadas.

            // Iterar por essa lista para exibir os títulos
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).tiutulo());
            }
        }
         */

        // (Iterar) Exibir títulos da série por temporadas utilizando função lambda
        /*
        Leia-se: para toda temporada t, pega-se os episódios dela. E também percorre-se os títulos de cada episódio
         */
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.tiutulo())));
    }
}
