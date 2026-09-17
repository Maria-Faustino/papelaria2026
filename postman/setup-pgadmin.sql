-- Execute no Query Tool do banco postgres (pgAdmin).
-- Senha alinhada com o arquivo .env do projeto.

CREATE ROLE papelaria_app
    WITH LOGIN
    PASSWORD 'postdba';

CREATE DATABASE papelaria_dev
    WITH OWNER papelaria_app;

CREATE DATABASE papelaria_test
    WITH OWNER papelaria_app;

-- Opcional (Aula 06):
-- CREATE DATABASE papelaria_diff WITH OWNER papelaria_app;
-- CREATE DATABASE papelaria_reference WITH OWNER papelaria_app;

-- Se o role já existir e a senha estiver diferente:
-- ALTER ROLE papelaria_app PASSWORD 'postdba';
