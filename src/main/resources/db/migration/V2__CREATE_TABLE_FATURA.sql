CREATE TABLE fatura
(
    id                   INTEGER AUTO_INCREMENT,
    mes                  VARCHAR(255) NOT NULL,
    documento            VARCHAR(255) NOT NULL,
    pago                 VARCHAR(255) NOT NULL,
    empresa_id           INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (empresa_id) REFERENCES empresa(codigo)
) ENGINE=InnoDB AUTO_INCREMENT=726721;