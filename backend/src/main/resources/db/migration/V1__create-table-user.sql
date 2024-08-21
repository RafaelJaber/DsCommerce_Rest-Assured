CREATE TABLE tb_user
(
    id         bigint IDENTITY (1, 1) NOT NULL,
    name       varchar(255),
    email      varchar(255),
    phone      varchar(255),
    birth_date date,
    password   varchar(255),
    CONSTRAINT pk_tb_user PRIMARY KEY (id)
)
GO