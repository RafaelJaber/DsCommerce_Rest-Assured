CREATE TABLE tb_order
(
    id      bigint IDENTITY (1, 1) NOT NULL,
    moment  datetime2,
    status  varchar(255),
    user_id bigint,
    CONSTRAINT pk_tb_order PRIMARY KEY (id)
)
GO

ALTER TABLE tb_order
    ADD CONSTRAINT FK_TB_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES tb_user (id)
GO