CREATE TABLE tb_order_item
(
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity int,
    price float(53),
    CONSTRAINT pk_tb_order_item PRIMARY KEY (order_id, product_id)
)
GO

ALTER TABLE tb_order_item
    ADD CONSTRAINT fk_tb_order_item_on_product FOREIGN KEY (product_id) REFERENCES tb_product (id)
GO

ALTER TABLE tb_order_item
    ADD CONSTRAINT fk_tb_order_item_on_order FOREIGN KEY (order_id) REFERENCES tb_order (id)
GO

