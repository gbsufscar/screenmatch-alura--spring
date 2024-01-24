package br.com.alura.screenmatchspring;

import br.com.alura.screenmatchspring.model.DadosSerie;
import br.com.alura.screenmatchspring.service.ConsumoApi;
import br.com.alura.screenmatchspring.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchspringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchspringApplication.class, args);
	}

	// Método da interface CommandLineRunner (Contrato). Acaba sendo um método main.
	@Override
	public void run(String... args) throws Exception {
		// Chamar Classe ConsumoApi - consulta
		ConsumoApi consumoApi = new ConsumoApi();

		// Variável para receber o json da consulta
		/// Chave do site de busca OMDb
		String chave = "690913ef";

		/// Busca da Série
		String busca ="gilmore girls";

		/// Chamar método - Consulta API para obter o json usando a biblioteca GSON
		String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=" + chave;
		String json = consumoApi.obterDados(endereco); // Arg - endenço com a busca

		/// Exibir o resultado da consulta
		System.out.println("Exibir a consulta no formato json: \n" + json);

		/// Fazer a conversão da consulta (instancia o conversor, depois transforma o dado)
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class); // Deseja converter o json em DadosSerie

		/// Exibir os dados da consulta convertido no formato desejado
		/// Mostra representado da forma do toString do Record
		System.out.println("Informações convertidas no formato desejado: \n" + dados);

	}
}
