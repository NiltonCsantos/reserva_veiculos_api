-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-05-16 12:06:08.06

-- tables
-- Table: imv_imagen_veiculo
CREATE TABLE imv_imagen_veiculo (
                                    imv_nr_id bigserial  NOT NULL,
                                    imv_tx_nome varchar(150)  NOT NULL,
                                    imv_bt_bytes bytea  NOT NULL,
                                    imv_tx_extensao varchar(4)  NOT NULL,
                                    vei_nr_id bigserial  NOT NULL,
                                    CONSTRAINT imv_imagen_veiculo_pk PRIMARY KEY (imv_nr_id)
);

-- Table: per_perfil
CREATE TABLE per_perfil (
                            per_nr_id bigserial  NOT NULL,
                            per_tx_nome varchar(50)  NOT NULL,
                            CONSTRAINT per_perfil_pk PRIMARY KEY (per_nr_id)
);

-- Table: usp_usuario_perfil
CREATE TABLE usp_usuario_perfil (
                                    usu_nr_id bigserial  NOT NULL,
                                    per_nr_id bigserial  NOT NULL,
                                    CONSTRAINT usp_usuario_perfil_pk PRIMARY KEY (usu_nr_id,per_nr_id)
);

-- Table: usu_usuario
CREATE TABLE usu_usuario (
                             usu_nr_id bigserial  NOT NULL,
                             usu_tx_nome varchar(100)  NOT NULL,
                             usu_tx_login varchar(100)  NOT NULL,
                             usu_tx_senha text  NOT NULL,
                             CONSTRAINT usu_usuario_pk PRIMARY KEY (usu_nr_id)
);

-- Table: vei_veiculo
CREATE TABLE vei_veiculo (
                             vei_nr_id bigserial  NOT NULL,
                             vei_tx_nome varchar(100)  NOT NULL,
                             vei_tx_marca varchar(50)  NOT NULL,
                             vei_tx_tipo varchar(50)  NOT NULL,
                             CONSTRAINT vei_veiculo_pk PRIMARY KEY (vei_nr_id)
);

-- Table: vus_veiculo_usuario
CREATE TABLE vus_veiculo_usuario (
                                     vus_nr_id bigserial  NOT NULL,
                                     vus_dt_date date  NOT NULL,
                                     vei_nr_id bigserial  NOT NULL,
                                     usu_nr_id bigserial  NOT NULL,
                                     CONSTRAINT vus_veiculo_usuario_pk PRIMARY KEY (vus_nr_id)
);

-- foreign keys
-- Reference: imv_imagens_veiculos_vei_veiculos (table: imv_imagen_veiculo)
ALTER TABLE imv_imagen_veiculo ADD CONSTRAINT imv_imagens_veiculos_vei_veiculos
    FOREIGN KEY (vei_nr_id)
        REFERENCES vei_veiculo (vei_nr_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: usp_usuario_perfil_per_perfil (table: usp_usuario_perfil)
ALTER TABLE usp_usuario_perfil ADD CONSTRAINT usp_usuario_perfil_per_perfil
    FOREIGN KEY (per_nr_id)
        REFERENCES per_perfil (per_nr_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: usp_usuario_perfil_usu_usuario (table: usp_usuario_perfil)
ALTER TABLE usp_usuario_perfil ADD CONSTRAINT usp_usuario_perfil_usu_usuario
    FOREIGN KEY (usu_nr_id)
        REFERENCES usu_usuario (usu_nr_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: vus_veiculo_usuario_usu_usuario (table: vus_veiculo_usuario)
ALTER TABLE vus_veiculo_usuario ADD CONSTRAINT vus_veiculo_usuario_usu_usuario
    FOREIGN KEY (usu_nr_id)
        REFERENCES usu_usuario (usu_nr_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: vus_veiculo_usuario_vei_veiculos (table: vus_veiculo_usuario)
ALTER TABLE vus_veiculo_usuario ADD CONSTRAINT vus_veiculo_usuario_vei_veiculos
    FOREIGN KEY (vei_nr_id)
        REFERENCES vei_veiculo (vei_nr_id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

