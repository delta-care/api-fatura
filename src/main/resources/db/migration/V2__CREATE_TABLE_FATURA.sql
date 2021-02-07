CREATE TABLE fatura
(
    id                   INTEGER AUTO_INCREMENT,
    mes                  VARCHAR(255) NOT NULL,
    documento            VARCHAR(255) NOT NULL,
    empresa_id           INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id, documento),
    FOREIGN KEY (empresa_id) REFERENCES empresa(id)
) ENGINE=InnoDB AUTO_INCREMENT=726721;