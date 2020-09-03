CREATE DATABASE taskmanagerdatabase ENCODING = 'UTF-8'

CREATE SCHEMA IF NOT EXISTS taskmanager

CREATE TABLE taskmanager.task (
	id bigserial NOT NULL,
	titulo varchar(100) NOT NULL,
	descricao varchar(200),
	status character varying(20) check( status in ('CONCLUIDO', 'PENDENTE') ) not null,
	data_cadastro date,
	data_editado date,
	data_removido date,
	data_concluido date,
	CONSTRAINT task_pkey PRIMARY KEY (id)
);