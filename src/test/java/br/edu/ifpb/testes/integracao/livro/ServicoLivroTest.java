package br.edu.ifpb.testes.integracao.livro;

import br.edu.ifpb.testes.integracao.GenericDatabaseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServicoLivroTest extends GenericDatabaseTestCase {

    private ServicoLivro servicoLivro;
//    private JdbcDatabaseContainer container;
//    private Connection conexao;

    @Before
    public void setUp() throws SQLException {
//        if(container == null) {
//            PostgreSQLContainer postgreSQLContainer;
//            postgreSQLContainer = new PostgreSQLContainer().withUsername("postgresql").withPassword("123456").withDatabaseName("exemplo-testes");
////            getClass().getResource("/postgres/create_schema.sql");
//            container = postgreSQLContainer.withInitScript("postgres/create_schema.sql");
//            container.start();
//            conexao = DriverManager.getConnection(container.getJdbcUrl(), "postgresql", "123456");
//        }
//        RepositorioLivro repositorioLivro = new RepositorioLivroImpl(conexao);

        RepositorioLivro repositorioLivro = new RepositorioLivroImpl();
        this.servicoLivro = new ServicoLivroImpl(repositorioLivro);
    }

    @Test
    public void livroValido() throws LivroInvalidoException {
        Livro livro = new Livro("Como quebrar um software");
        servicoLivro.salvar(livro);
        Livro livroRegistrado = servicoLivro.consultar("Como quebrar um software");
        assertNotNull(livroRegistrado);
        assertNotNull(livroRegistrado.getId());
    }

    @Test(expected = LivroInvalidoException.class)
    public void livroInvalido() throws LivroInvalidoException {
        Livro livro = new Livro(null);
        servicoLivro.salvar(livro);
    }

    @Test(expected = LivroInvalidoException.class)
    public void livroNulo() throws LivroInvalidoException {
        Livro livro = null;
        servicoLivro.salvar(livro);
    }

    @Test(expected = LivroInvalidoException.class)
    public void livroAcimaMaxCaracteres() throws LivroInvalidoException {
        Livro livro = new Livro("Como quebrar um software. 1001 receitas de como encontra um bug e deixar um desenvolvedor puto da vida");
        servicoLivro.salvar(livro);
    }

//    @After
//    public void tearDown() throws SQLException {
//        conexao.close();
//    }

    @Override
    public String getDataSetFile() {
        return "src/test/resources/emprestimos_testset.xml";
    }
}