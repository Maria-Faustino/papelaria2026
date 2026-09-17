# O que fazer no pgAdmin para o código funcionar

O Spring Boot **não cria o usuário nem os bancos**. Ele só conecta no PostgreSQL e o Liquibase cria as tabelas. Sem o passo abaixo, a aplicação sobe com erro de autenticação ou de banco inexistente.

Valores usados neste projeto:

| Item | Valor |
|---|---|
| Host | `localhost` |
| Porta | `5432` |
| Usuário da aplicação | `papelaria_app` |
| Senha | `postdba` |
| Banco de desenvolvimento | `papelaria_dev` |
| Banco de testes | `papelaria_test` |

Esses valores precisam ser **iguais** aos do arquivo `.env` na raiz do projeto.

## 1. Confirmar que o PostgreSQL está ligado

No pgAdmin, o servidor (geralmente `PostgreSQL 16` ou `PostgreSQL 17`) precisa aparecer com o ícone de conectado. Se pedir senha, use a senha do usuário `postgres` que você definiu na instalação.

## 2. Abrir o Query Tool no banco `postgres`

1. Expanda o servidor.
2. Expanda **Databases**.
3. Clique com o botão direito em `postgres` → **Query Tool**.

Não execute o script dentro de `papelaria_dev` antes de o banco existir.

## 3. Criar o usuário e os bancos

Cole e execute **um comando de cada vez** (o pgAdmin às vezes reclama de `CREATE DATABASE` no meio de uma transação):

```sql
CREATE ROLE papelaria_app
    WITH LOGIN
    PASSWORD 'postdba';
```

```sql
CREATE DATABASE papelaria_dev
    WITH OWNER papelaria_app;
```

```sql
CREATE DATABASE papelaria_test
    WITH OWNER papelaria_app;
```

Opcional, só se for usar o laboratório de comparação de esquema da Aula 06:

```sql
CREATE DATABASE papelaria_diff
    WITH OWNER papelaria_app;
```

```sql
CREATE DATABASE papelaria_reference
    WITH OWNER papelaria_app;
```

Se o usuário já existir, o pgAdmin mostra `ERROR: role "papelaria_app" already exists`. Isso não é problema: pule esse comando ou alinhe a senha:

```sql
ALTER ROLE papelaria_app PASSWORD 'postdba';
```

Se o banco já existir, pule o `CREATE DATABASE` correspondente.

## 4. Conferir se deu certo

```sql
SELECT rolname FROM pg_roles WHERE rolname = 'papelaria_app';
```

```sql
SELECT datname FROM pg_database
WHERE datname IN ('papelaria_dev', 'papelaria_test');
```

O resultado precisa mostrar o usuário e os dois bancos.

## 5. Registrar o servidor da aplicação no pgAdmin (opcional, mas útil)

Para ver as tabelas depois que o Spring subir:

1. Clique com o botão direito em **Servers** → **Register** → **Server**.
2. Aba **General**: nome `papelaria`.
3. Aba **Connection**:
   - Host: `localhost`
   - Port: `5432`
   - Maintenance database: `papelaria_dev`
   - Username: `papelaria_app`
   - Password: `postdba`
4. Salve.

## 6. Conferir o arquivo `.env`

Na raiz do projeto, o `.env` precisa ter a **mesma** senha usada no SQL:

```dotenv
DB_DEV_URL=jdbc:postgresql://localhost:5432/papelaria_dev
DB_DEV_USERNAME=papelaria_app
DB_DEV_PASSWORD=postdba

DB_TEST_URL=jdbc:postgresql://localhost:5432/papelaria_test
DB_TEST_USERNAME=papelaria_app
DB_TEST_PASSWORD=postdba
```

Não coloque a senha entre aspas. Não envie o `.env` para o GitHub.

## 7. Subir a aplicação

No IntelliJ, use o profile `dev`. No terminal, a partir da pasta do projeto:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Na primeira execução o Liquibase cria as tabelas `categoria`, `produto` e `fornecedor` dentro de `papelaria_dev`.

No pgAdmin: `papelaria_dev` → **Schemas** → `public` → **Tables**. Devem aparecer:

- `categoria`
- `produto`
- `fornecedor`
- `databasechangelog`
- `databasechangeloglock`

## 8. Erros comuns

| Erro | Causa | O que fazer |
|---|---|---|
| `password authentication failed` | senha do `.env` diferente da do SQL | altere o `.env` ou rode `ALTER ROLE papelaria_app PASSWORD 'postdba';` |
| `database "papelaria_dev" does not exist` | o `CREATE DATABASE` não foi executado | volte ao passo 3 |
| `role "papelaria_app" does not exist` | o `CREATE ROLE` não foi executado | volte ao passo 3 |
| `Connection refused` | PostgreSQL parado | inicie o serviço do PostgreSQL no Windows/Linux |
| `Failed to determine a suitable driver class` / profile `none` | a aplicação subiu sem profile | rode com `-Dspring-boot.run.profiles=dev` |

Você **não** precisa criar as tabelas na mão. Só usuário, senha e bancos.
