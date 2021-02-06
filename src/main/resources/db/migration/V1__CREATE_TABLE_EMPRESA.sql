CREATE TABLE empresa
(
    id                 INTEGER AUTO_INCREMENT,
    codigo             INTEGER NOT NULL,
    cnpj               VARCHAR(255) NOT NULL,
    nome               VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (codigo),
    UNIQUE (cnpj)
) ENGINE=InnoDB;