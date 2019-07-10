package br.edu.ifpb.testes.integracao.emprestimo;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioEmprestimo {

    void salvarEmprestimo(Emprestimo emprestimo);
    void encerrarEmprestimo(Emprestimo emprestimo);
    Emprestimo consultar(Long codigoEmprestimo) throws EmprestimoException;
    List<Emprestimo> listarAtivos();
    List<Emprestimo> listarEncerrados();
    List<Emprestimo> listarTodos();

}