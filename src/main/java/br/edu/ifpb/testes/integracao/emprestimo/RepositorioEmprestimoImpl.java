package br.edu.ifpb.testes.integracao.emprestimo;

import br.edu.ifpb.testes.integracao.connection.ConnectionFactory;
import br.edu.ifpb.testes.integracao.livro.Livro;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RepositorioEmprestimoImpl implements RepositorioEmprestimo {

    private Connection conexao;

    public RepositorioEmprestimoImpl() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public RepositorioEmprestimoImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void salvarEmprestimo(Emprestimo emprestimo) {
        String insercao = "INSERT INTO emprestimo(id_livro, data_inicio, data_prevista_fim) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = this.conexao.prepareStatement(insercao);
            preparedStatement.setLong(1, emprestimo.getLivro().getId());
            preparedStatement.setDate(2, Date.valueOf(emprestimo.getDataInicio()));
            preparedStatement.setDate(3, Date.valueOf(emprestimo.getDataPrevistaFim()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void encerrarEmprestimo(Emprestimo emprestimo) {
        String update = "UPDATE emprestimo SET data_fim = ? WHERE id_livro = ?";
        try {
            PreparedStatement preparedStatement = this.conexao.prepareStatement(update);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
            preparedStatement.setLong(2, emprestimo.getLivro().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Emprestimo consultar(Long codigoEmprestimo) throws EmprestimoException {
        String query = "SELECT e.id, e.data_inicio, e.data_prevista_fim, e.data_fim, l.id, l.nome FROM emprestimo e, livro l WHERE e.id_emprestimo = ? AND e.id_livro = l.id";
        Emprestimo emprestimo = null;
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Livro livro = new Livro(resultSet.getLong(5), resultSet.getString(6));
                emprestimo = new Emprestimo(
                        resultSet.getLong(1),
                        livro,
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(4).toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (emprestimo == null)
            throw new EmprestimoException("Empréstimo inexistente");
        return emprestimo;
    }

    @Override
    public List<Emprestimo> listarAtivos() {
        String query = "SELECT e.id, e.data_inicio, e.data_prevista_fim, e.data_fim, l.id, l.nome FROM emprestimo e, livro l WHERE e.id_livro = l.id AND e.data_fim IS NULL";
        return processQuery(query);
    }

    @Override
    public List<Emprestimo> listarEncerrados() {
        String query = "SELECT e.id, e.data_inicio, e.data_prevista_fim, e.data_fim, l.id, l.nome FROM emprestimo e, livro l WHERE e.id_livro = l.id AND e.data_fim IS NOT NULL";
        return processQuery(query);
    }

    @Override
    public List<Emprestimo> listarTodos() {
        String query = "SELECT e.id, e.data_inicio, e.data_prevista_fim, e.data_fim, l.id, l.nome FROM emprestimo e, livro l WHERE e.id_livro = l.id";
        return processQuery(query);
    }

    private List<Emprestimo> processQuery(String query) {
        List<Emprestimo> emprestimos = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Livro livro = new Livro(resultSet.getLong(5), resultSet.getString(6));
                Emprestimo emprestimo = new Emprestimo(
                        resultSet.getLong(1),
                        livro,
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getDate(3).toLocalDate(),
                        null);
                if (resultSet.getDate(4) != null)
                    emprestimo.setDataFim(resultSet.getDate(4).toLocalDate());
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }
}
