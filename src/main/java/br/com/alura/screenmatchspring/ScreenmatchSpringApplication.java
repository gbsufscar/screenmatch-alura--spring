package br.com.alura.screenmatchspring;

import br.com.alura.screenmatchspring.model.DadosEpisodios;
import br.com.alura.screenmatchspring.model.DadosSerie;
import br.com.alura.screenmatchspring.model.DadosTemporada;
import br.com.alura.screenmatchspring.service.ConsumoApi;
import br.com.alura.screenmatchspring.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchSpringApplication.class, args);
	}

	// Método da interface CommandLineRunner (Contrato). Acaba sendo um método main.
	@Override
	public void run(String... args) throws Exception {
		// Chamar Classe ConsumoApi - consulta
		ConsumoApi consumoApi = new ConsumoApi();

		// Variável para receber o json da consulta
		/// Chave do site de busca OMDb (https://omdbapi.com/)
		String chave = "690913ef";

		/// Busca da Série
		String busca ="gilmore girls";

		/// Chamar método - Consulta API para obter o json usando a biblioteca GSON
		String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=" + chave;
		String json = consumoApi.obterDados(endereco); // Arg - endereço com a busca

		/// Exibir o resultado da consulta
		//System.out.println("Exibir a consulta no formato json: \n" + json);

		/// Fazer a conversão da consulta (instancia o conversor, depois transforma o dado)
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class); // Deseja converter o json em DadosSerie

		/// Exibir os dados da consulta convertido no formato desejado
		/// Mostra representado da forma do toString do Record
		System.out.println("SÉRIE - Informações convertidas no formato desejado: \n" + dados);
		System.out.println("******** \n");


		// Busca de Informações de um Episódio
		/// Dados para busca de informações
		busca ="gilmore girls"; // Já tipada anteriormente
		String temporada = "1";
		String episodio = "2";

		/// Chamar método - Consulta API para obter o json usando a biblioteca GSON
		endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+")
				+ "&season=" + temporada
				+ "&episode=" + episodio
				+ "&apikey=" + chave;

		/// Obter o json da consulta
		json = consumoApi.obterDados(endereco); // Arg - endereço com a busca

		/// Exibir o resultado da consulta
		//System.out.println("Exibir a consulta no formato json: \n" + json);

		/// Atualizar a variável json
		json = consumoApi.obterDados(endereco); // Arg - endenço com a busca

		/// Fazer a conversão da consulta (com conversor já instaciado acima, depois transforma o dado)
		DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);

		/// Exibir os dados da consulta convertido no formato desejado
		/// Mostra representado da forma do toString do Record
		System.out.println("EPISÓDIO - Informações convertidas no formato desejado: \n" + dadosEpisodios);
		System.out.println("******** \n");


		// Busca de Informações de uma Temporada
		/// Usar um laço for para iterar sobre o total de temporadas
		List<DadosTemporada> temporadas = new ArrayList<>(); // Instancia a lista do tipo Dados Temporada
		for (int i = 1; i <= dados.totalTemporadas(); i++) { // dados foi delcarado na busca de série
			/// Dados para busca de informações
			busca = "gilmore girls"; // Já tipada anteriormente
			int temporadaLaco = i; // Busca a temporada a cada laço de repetição

			/// Chamar método - Consulta API para obter o json usando a biblioteca GSON
			endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+")
					+ "&season=" + temporadaLaco
					+ "&apikey=" + chave;

			/// Obter o json da consulta
			json = consumoApi.obterDados(endereco); // Arg - endereço com a busca

			/// Instanciar Dados Temporada, converter e transformar o json
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);

			/// Adicionar as temporadas na lista de temporadas
			temporadas.add(dadosTemporada);
		}

		/// Exibir os dados da consulta convertido no formato desejado
		System.out.println("TEMPORADAS - Informações convertidas no formato desejado:");
		temporadas.forEach(System.out::println);
		System.out.println("******** \n");

	}
}
