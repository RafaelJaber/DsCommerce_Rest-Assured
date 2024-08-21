CREATE TABLE tb_category
(
    id   bigint IDENTITY (1, 1) NOT NULL,
    name varchar(255),
    CONSTRAINT pk_tb_category PRIMARY KEY (id)
)
GO

CREATE TABLE tb_product
(
    id          bigint IDENTITY (1, 1) NOT NULL,
    name        varchar(255),
    description varchar(max),
    price       float(53),
    img_url     varchar(255),
    CONSTRAINT pk_tb_product PRIMARY KEY (id)
)
GO

CREATE TABLE tb_product_category
(
    category_id bigint NOT NULL,
    product_id  bigint NOT NULL,
    CONSTRAINT pk_tb_product_category PRIMARY KEY (category_id, product_id)
)
GO

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_email UNIQUE (email)
GO

ALTER TABLE tb_product_category
    ADD CONSTRAINT fk_tbprocat_on_category FOREIGN KEY (category_id) REFERENCES tb_category (id)
GO

ALTER TABLE tb_product_category
    ADD CONSTRAINT fk_tbprocat_on_product FOREIGN KEY (product_id) REFERENCES tb_product (id)
GO