package br.edu.ifpb.testes.integracao.emprestimo;

import br.edu.ifpb.testes.integracao.livro.Livro;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServicoEmprestimoImpl implements ServicoEmprestimo {

    private RepositorioEmprestimo repositorioEmprestimo;

    public ServicoEmprestimoImpl(RepositorioEmprestimo repositorioEmprestimo) {
        this.repositorioEmprestimo = repositorioEmprestimo;
    }

    public void emprestar(Livro livro) throws EmprestimoException {
        List<Emprestimo> emprestimos = repositorioEmprestimo.listarAtivos();
        Optional<Emprestimo> emprestimoExistente = emprestimos.stream()
                .filter(x -> livro.getNome().equals(x.getLivro().getNome())).findFirst();
        if(emprestimoExistente.isPresent()) {
            throw new EmprestimoException("Livro já está emprestado");
        }
        Emprestimo emprestimo = new Emprestimo(livro, LocalDate.now(), LocalDate.now().plusDays(5));
        this.repositorioEmprestimo.salvarEmprestimo(emprestimo);
    }

    public void devolver(Livro livro) throws EmprestimoException {
        List<Emprestimo> emprestimos = repositorioEmprestimo.listarAtivos();
        Optional<Emprestimo> emprestimoExistente = emprestimos.stream()
                .filter(x -> livro.getNome().equals(x.getLivro().getNome())).findFirst();
        if(emprestimoExistente.isPresent()) {
            this.repositorioEmprestimo.encerrarEmprestimo(emprestimoExistente.get());
        } else throw new EmprestimoException("Livro não está emprestado!");
    }

    @Override
    public Emprestimo consultar(Long codigoEmprestimo) throws EmprestimoException {
        Emprestimo emprestimo = repositorioEmprestimo.consultar(codigoEmprestimo);
        if(emprestimo == null)
            throw new EmprestimoException("Empréstimo inexistente com código " + codigoEmprestimo);
        return emprestimo;
    }

    @Override
    public List<Emprestimo> listarAtivos() {
        return repositorioEmprestimo.listarAtivos();
    }

    @Override
    public List<Emprestimo> listarEncerrados() {
        return repositorioEmprestimo.listarEncerrados();
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return repositorioEmprestimo.listarTodos();
    }
}
