package br.edu.ifpb.testes.integracao.livro;

import java.util.List;

public class ServicoLivroImpl implements ServicoLivro {

    private RepositorioLivro repositorioLivro;

    public ServicoLivroImpl(RepositorioLivro repositorioLivro) {
        this.repositorioLivro = repositorioLivro;
    }

    @Override
    public void salvar(Livro livro) throws LivroInvalidoException {
        if(livro == null)
            throw new LivroInvalidoException("Livro não pode ser nulo!");
        if(livro.getNome() == null || livro.getNome().length() > 80)
            throw new LivroInvalidoException("Livro deve conter nome e ser menor que 80 caracteres");
        this.repositorioLivro.salvar(livro);
    }

    @Override
    public Livro consultar(String nome) throws LivroInvalidoException {
        return this.repositorioLivro.buscarPorNome(nome);
    }

    @Override
    public List<Livro> listar() {
        return null;
    }
}
