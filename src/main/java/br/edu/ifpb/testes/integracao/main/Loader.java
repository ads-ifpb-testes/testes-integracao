package br.edu.ifpb.testes.integracao.main;

import br.edu.ifpb.testes.integracao.livro.*;

public class Loader {

    public static void main(String[] args) throws LivroInvalidoException {
        Livro livro = new Livro("How to Break Software");

        RepositorioLivro repositorioLivro = new RepositorioLivroImpl();
        ServicoLivro servicoLivro = new ServicoLivroImpl(repositorioLivro);

        servicoLivro.salvar(livro);
    }

}
