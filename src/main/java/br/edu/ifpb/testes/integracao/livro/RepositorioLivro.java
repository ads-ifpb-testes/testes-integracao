package br.edu.ifpb.testes.integracao.livro;

import java.util.List;

public interface RepositorioLivro {

    void salvar(Livro livro);
    List<Livro> listarTodos();
    Livro buscarPorNome(String nome) throws LivroInvalidoException;

}
