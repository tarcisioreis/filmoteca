CREATE TABLE genero (
	codigo  bigint(20) NOT NULL COMMENT 'Chave primária.\r\n\r\n\r\nObrigatório.',
    nome  text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Nome do Gênero.\r\n\r\n\r\nObrigatório.'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE genero
ADD PRIMARY KEY (codigo);

ALTER TABLE genero
MODIFY codigo bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Chave primária.\r\n\r\n\r\nObrigatório.',AUTO_INCREMENT=1;

CREATE TABLE filme (
	codigo bigint(20) NOT NULL COMMENT 'Chave primária.\r\n\r\n\r\nObrigatório.',
    titulo text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Título do Filme.\r\n\r\n\r\nObrigatório.',
    diretor text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'Diretor do Filme.',
    anoLancamento int(2) NULL COMMENT 'Ano de Lançamento do Filme',
    genero bigint(20) NOT NULL COMMENT 'Genero do Filme'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE filme
ADD PRIMARY KEY (codigo);

ALTER TABLE filme
MODIFY codigo bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Chave primária.\r\n\r\n\r\nObrigatório.',AUTO_INCREMENT=1;

ALTER TABLE filme
ADD CONSTRAINT Genero_cfk FOREIGN KEY (genero) REFERENCES genero (codigo) ON UPDATE CASCADE;
