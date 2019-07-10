package br.edu.ifpb.testes.integracao.emprestimo;

import br.edu.ifpb.testes.integracao.livro.Livro;

import java.time.LocalDate;
import java.util.List;

public interface ServicoEmprestimo {

    void emprestar(Livro livro) throws EmprestimoException;
    void devolver(Livro livro) throws EmprestimoException;
    Emprestimo consultar(Long codigoEmprestimo) throws EmprestimoException;
    List<Emprestimo> listarAtivos();
    List<Emprestimo> listarEncerrados();
    List<Emprestimo> listarTodos();

}