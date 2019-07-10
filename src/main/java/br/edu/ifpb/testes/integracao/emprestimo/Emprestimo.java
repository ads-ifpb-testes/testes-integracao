package br.edu.ifpb.testes.integracao.emprestimo;

import br.edu.ifpb.testes.integracao.livro.Livro;

import java.time.LocalDate;

public class Emprestimo {

    private Long id;
    private Livro livro;
    private LocalDate dataInicio;
    private LocalDate dataPrevistaFim;
    private LocalDate dataFim;

    public Emprestimo(Long id, Livro livro, LocalDate dataInicio, LocalDate dataPrevistaFim, LocalDate dataFim) {
        this.id = id;
        this.livro = livro;
        this.dataInicio = dataInicio;
        this.dataPrevistaFim = dataPrevistaFim;
        this.dataFim = dataFim;
    }

    public Emprestimo(Livro livro, LocalDate dataInicio, LocalDate dataPrevistaFim) {
        this.livro = livro;
        this.dataInicio = dataInicio;
        this.dataPrevistaFim = dataPrevistaFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataPrevistaFim() {
        return dataPrevistaFim;
    }

    public void setDataPrevistaFim(LocalDate dataPrevistaFim) {
        this.dataPrevistaFim = dataPrevistaFim;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}
