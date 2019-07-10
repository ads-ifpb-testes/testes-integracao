package br.edu.ifpb.testes.integracao.livro;

import br.edu.ifpb.testes.integracao.connection.ConnectionFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RepositorioLivroImpl implements RepositorioLivro {

    private Connection conexao;

    public RepositorioLivroImpl() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public RepositorioLivroImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void salvar(Livro livro) {
        String insercao = "INSERT INTO livro(nome) VALUES (?)";
        try {
            PreparedStatement preparedStatement = this.conexao.prepareStatement(insercao);
            preparedStatement.setString(1, livro.getNome());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livro> listarTodos() {
        String query = "SELECT * FROM livro";
        return processQuery(query);
    }

    @Override
    public Livro buscarPorNome(String nome) throws LivroInvalidoException {
        String query = "SELECT * FROM livro l WHERE UPPER(l.nome) = ?";
        Livro livro = null;
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
            preparedStatement.setString(1, nome.toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                livro = new Livro(resultSet.getLong(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (livro == null)
            throw new LivroInvalidoException("Este livro não existe");
        return livro;
    }

    private List<Livro> processQuery(String query) {
        List<Livro> livros = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Livro livro = new Livro(resultSet.getLong(1), resultSet.getString(2));
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }
}
