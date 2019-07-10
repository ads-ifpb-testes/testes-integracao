package br.edu.ifpb.testes.integracao.livro;

import br.edu.ifpb.testes.integracao.GenericDatabaseTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServicoLivroTest {

    private ServicoLivro servicoLivro;
    private JdbcDatabaseContainer container;
    private Connection conexao;
    private IDatabaseTester databaseTester;

    @Before
    public void setUp() throws Exception {
        if(container == null) {
            PostgreSQLContainer postgreSQLContainer;
            postgreSQLContainer = new PostgreSQLContainer().withUsername("postgresql").withPassword("123456").withDatabaseName("exemplo-testes");
            container = postgreSQLContainer.withInitScript("postgres/create_schema.sql");
            container.start();
            conexao = DriverManager.getConnection(container.getJdbcUrl(), "postgresql", "123456");
        }
        configureDBUnit();
        this.databaseTester.onSetup();
        RepositorioLivro repositorioLivro = new RepositorioLivroImpl(conexao);
        this.servicoLivro = new ServicoLivroImpl(repositorioLivro);
    }

    private void configureDBUnit() throws DataSetException, FileNotFoundException, ClassNotFoundException {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(("src/test/resources/livros_testset.xml")));
        this.databaseTester = new JdbcDatabaseTester("org.postgresql.Driver",
                container.getJdbcUrl(), "postgresql", "123456");
        this.databaseTester.setDataSet(dataSet);
        this.databaseTester.setSetUpOperation(DatabaseOperation.INSERT);
        this.databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
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
    public void livroJaExistente() throws LivroInvalidoException {
        Livro livro = new Livro("How to Break Software");
        servicoLivro.salvar(livro);
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

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
        conexao.close();
    }
}