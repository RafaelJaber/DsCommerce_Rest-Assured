CREATE TABLE tb_payment
(
    order_id bigint NOT NULL,
    moment   datetime2,
    CONSTRAINT pk_tb_payment PRIMARY KEY (order_id)
)
GO

ALTER TABLE tb_payment
    ADD CONSTRAINT FK_TB_PAYMENT_ON_ORDER FOREIGN KEY (order_id) REFERENCES tb_order (id)
GO