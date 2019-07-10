package br.edu.ifpb.testes.integracao.livro;

import java.util.List;

public interface ServicoLivro {

    void salvar(Livro livro) throws LivroInvalidoException;
    Livro consultar(String nome) throws LivroInvalidoException;
    List<Livro> listar();

}
