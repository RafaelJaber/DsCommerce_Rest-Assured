ALTER TABLE tb_user
    ADD is_active bit
        CONSTRAINT DF_tb_user_is_active DEFAULT 1
GO