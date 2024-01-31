package br.com.alura.screenmatchspring.model;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    // Atributos
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;


    // Construtores da Classe
    public Episodio(Integer numeroTemporada, DadosEpisodios dadosEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodios.titulo();
        this.numeroEpisodio = dadosEpisodios.numero();
        // Tratar a avaliação diretamente no construtor
        try {
            this.avaliacao = Double.valueOf(dadosEpisodios.avaliacao());
        } catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        // Tratar os dados de datas
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodios.dataLancamento());
        } catch (DateTimeParseException ex){
            this.dataLancamento = null;
        }

    }


    // Métodos Getters e Setters

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numeroEpisodio;
    }

    public void setNumero(Integer numero) {
        this.numeroEpisodio = numero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }


    // Método toString para exibir as informações dos Episódios


    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }
}
