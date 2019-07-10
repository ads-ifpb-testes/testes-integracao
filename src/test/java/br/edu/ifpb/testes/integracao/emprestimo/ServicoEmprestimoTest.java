package br.edu.ifpb.testes.integracao.emprestimo;

import br.edu.ifpb.testes.integracao.livro.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ServicoEmprestimoTest {

    private ServicoEmprestimo servicoEmprestimo;
    private ServicoLivro servicoLivro;

    @Before
    public void setUp() {
        RepositorioEmprestimo repositorioEmprestimo = new RepositorioEmprestimoImpl();
        this.servicoEmprestimo = new ServicoEmprestimoImpl(repositorioEmprestimo);
        RepositorioLivro repositorioLivro = new RepositorioLivroImpl();
        this.servicoLivro = new ServicoLivroImpl(repositorioLivro);
    }

    @Test
    public void emprestar() throws EmprestimoException, LivroInvalidoException {
        Livro livro = new Livro("Como quebrar um software");
        servicoLivro.salvar(livro);

        final Livro livroFinal = servicoLivro.consultar("Como quebrar um software");

        servicoEmprestimo.emprestar(livroFinal);
        List<Emprestimo> emprestimoList = servicoEmprestimo.listarAtivos();

        Optional<Emprestimo> emprestimoOptional = emprestimoList.stream().filter(x -> x.getLivro().getNome().equals(livroFinal.getNome())).findFirst();
        assertTrue(emprestimoOptional.isPresent());
    }

    @Test
    public void devolver() throws EmprestimoException, LivroInvalidoException {
        Livro livro = new Livro("Como quebrar um software");
        servicoLivro.salvar(livro);

        final Livro livroFinal = servicoLivro.consultar("Como quebrar um software");

        servicoEmprestimo.emprestar(livroFinal);
        servicoEmprestimo.devolver(livro);
        List<Emprestimo> emprestimoList = servicoEmprestimo.listarEncerrados();
        Optional<Emprestimo> emprestimoEncerradoOptional = emprestimoList.stream().filter(x -> x.getLivro().getNome().equals(livroFinal.getNome())).findFirst();
        assertTrue(emprestimoEncerradoOptional.isPresent());
    }
}