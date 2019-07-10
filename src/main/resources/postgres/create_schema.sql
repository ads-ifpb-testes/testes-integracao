CREATE TABLE livro(
  id serial,
  nome character varying(80) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE emprestimo(
  id serial,
  id_livro int,
  data_inicio date NOT NULL,
  data_prevista_fim date NOT NULL,
  data_fim date,
  PRIMARY KEY (id)
);