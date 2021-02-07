CREATE TABLE empresa
(
    id                 INTEGER AUTO_INCREMENT,
    codigo             INTEGER NOT NULL,
    cnpj               VARCHAR(255) NOT NULL,
    nome               VARCHAR(255) NOT NULL,
    email              VARCHAR(255) NOT NULL,
    logradouro         VARCHAR(255) NOT NULL,
    bairro             VARCHAR(255) NOT NULL,
    uf                 VARCHAR(255) NOT NULL,
    cep                VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (codigo, cnpj)
) ENGINE=InnoDB;