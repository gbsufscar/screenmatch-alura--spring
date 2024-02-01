package br.com.alura.screenmatchspring.principal;

import br.com.alura.screenmatchspring.model.DadosEpisodios;
import br.com.alura.screenmatchspring.model.DadosSerie;
import br.com.alura.screenmatchspring.model.DadosTemporada;
import br.com.alura.screenmatchspring.model.Episodio;
import br.com.alura.screenmatchspring.service.ConsumoApi;
import br.com.alura.screenmatchspring.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // (Iterar) Exibir títulos da série por temporadas utilizando função lambda
        /*
        Leia-se: para toda temporada t, pega-se os episódios dela. E também percorre-se os títulos de cada episódio
        (parametro) -> expressão
         */
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
        System.out.println("********");


        // Buscar as 5 Séries melhores avaliadas
        /// Declarar uma lista para reunir as informações dos espisódios via stream (fluxo de informações encadeadas)
        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) // Para trabalhar com lista dentro de outra lista
                .collect(Collectors.toList()); // Acrescenta à nova lista (que é mutável).

        /// Buscar as séries melhores avaliadas com ordenação decrescente
        System.out.println("\nExibir os Top 10 episódios melhores avaliados:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")) // Filtro para eliminar os episódios sem avaliação
                .peek(e -> System.out.println("Peek - Primeiro filtro - Ignorar N/A: " + e))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed()) // Itera comparando e ordena
                .peek(e -> System.out.println("Peek - Ordenação: "+ e))
                .limit(10) //Limita o número classificado
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Peek - Mapeamento: " + e))
                .forEach(System.out::println); // Finaliza a stream com a exibição dos episódios melhores classificados
        System.out.println("********\n");


        // Lista de Episódios
        /// Criar uma lista para a Objetos Episódios
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d))) // Número da temporada e várias informações dos episódios
                .collect(Collectors.toList());

        /// Exibir a lista de Episódios
        System.out.println("Exibir a lista de Episódios");
        episodios.forEach(System.out::println);
        System.out.println("********\n");


        // Buscar episódios por data
        System.out.println("A partir de qual ano você deseja ver os episódios?");
        var ano = leitura.nextInt();
        leitura.nextLine(); // Necessário para passar a linha

        /// Declarar a data de busca
        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        /// Formatar a data para a nomenclatura brasileira
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        /// Filtrar Episódito de Séries via uso de streams
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data de Lançamento: " + e.getDataLancamento().format(formatador)));
        System.out.println("********\n");


        // Buscar de título de episódio por palavra-chave utilizando o stream
        System.out.println("Digite o termo para busca do título do episódio da série: ");
        /// Leitura do teclado
        var trechoTiutlo = leitura.nextLine();

        // Declaração do optional para viabilizar a busca por episódios em uma stream
        Optional<Episodio> episodioBuscado = episodios.stream() // Classe Optional: tratar presença ou ausência de valores.
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTiutlo.toUpperCase())) // A comparação é feita em maiúscula.
                .findFirst(); // Retorna com a primeira referência do termo buscado.

        // Condição exibir se o episódio foi encontrado ou não
        if(episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println(("Temporada " + episodioBuscado.get().getTemporada()) + ", Título: " + episodioBuscado.get().getTitulo());
        } else {
            System.out.println("Inflelizmente o episódio não foi encontrado! Redefina o termo da busca.");
        }

    }

}
