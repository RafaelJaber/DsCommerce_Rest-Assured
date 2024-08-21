CREATE TABLE tb_role
(
    id        bigint IDENTITY (1, 1) NOT NULL,
    authority varchar(255),
    CONSTRAINT pk_tb_role PRIMARY KEY (id)
)
GO

CREATE TABLE tb_user_role
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT pk_tb_user_role PRIMARY KEY (role_id, user_id)
)
GO

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tb_user_role__on_role FOREIGN KEY (role_id) REFERENCES tb_role (id)
GO

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tb_user_role_ol_on_user FOREIGN KEY (user_id) REFERENCES tb_user (id)
GO