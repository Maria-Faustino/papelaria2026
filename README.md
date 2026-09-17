# Papelaria

API didática de controle de estoque de uma papelaria, desenvolvida conforme as aulas do curso [suporteos2026](https://github.com/jeffersonarpasserini/suporteos2026).

O projeto de referência controla grupos e produtos genéricos. Este repositório aplica a mesma arquitetura ao tema próprio: categorias, produtos de papelaria e fornecedores.

## Domínio

- Categoria: classificação dos itens (Escolar, Escritório, Arte, Embalagem).
- Produto: item identificado por código de barras, com saldo e valor unitário.
- Fornecedor: fabricante ou distribuidor opcional, identificado por CNPJ.

Consulte o [tema do projeto](docs/tema-do-projeto.md).

## Requisitos

- Java 21
- Git
- Maven Wrapper (já versionado)
- PostgreSQL (instalado localmente ou via Docker)
- pgAdmin para criar o usuário e os bancos

## Correspondência com as aulas

| Aula | Tema | O que este projeto implementa |
|---|---|---|
| 00–01 | GitHub e ambiente | Estrutura Maven, `.gitignore` e configuração local |
| 02 | Projeto e tema | Spring Boot, `/api/health` e domínio documentado |
| 03 | Modelagem com Java puro | Entidades com validação e comportamento, sem setters anêmicos |
| 04 | JPA, PostgreSQL, profiles e Liquibase | Profiles `dev`, `test` e `prod`; Hibernate apenas valida |
| 05 | Repositories, serviços e transações | Casos de uso transacionais e rollback |
| 06 | Evolução do modelo | Fornecedor, estoque mínimo e changelog revisado |
| 07 | API REST, DTOs e testes | Controllers, validação e respostas de erro padronizadas |

## O que fazer no pgAdmin

O código **não cria** o usuário nem os bancos. Antes de rodar, abra o Query Tool no banco `postgres` e execute:

```sql
CREATE ROLE papelaria_app
    WITH LOGIN
    PASSWORD 'postdba';

CREATE DATABASE papelaria_dev
    WITH OWNER papelaria_app;

CREATE DATABASE papelaria_test
    WITH OWNER papelaria_app;
```

A senha precisa ser a mesma do arquivo `.env` (`postdba`). O passo a passo completo, com conferência e erros comuns, está em [docs/pgadmin.md](docs/pgadmin.md).

## Collection Postman

Importe no Postman:

1. `postman/Papelaria.postman_collection.json`
2. `postman/Papelaria.postman_environment.json` (selecione o environment **Papelaria Local**)

Ordem sugerida: Health → Cadastrar Categoria → Cadastrar Fornecedor → Cadastrar Produto → listagens.

Os POSTs gravam automaticamente os IDs nas variáveis da collection.

## Executando o projeto

Na primeira execução, copie `.env.example` para `.env` se o arquivo ainda não existir, preencha `DB_DEV_PASSWORD` e `DB_TEST_PASSWORD` e mantenha esse arquivo fora do Git.

Se preferir Docker em vez do PostgreSQL instalado na máquina:

```bash
docker compose up -d
```

No Linux ou macOS:

```bash
cp .env.example .env
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Com a aplicação iniciada, acesse [http://localhost:8080/api/health](http://localhost:8080/api/health). A resposta esperada é `OK`.

## Modelo de domínio atual

```text
Categoria  1 ─────── N Produto
Fornecedor 0..1 ─── N Produto
```

O fornecedor de um produto é opcional. As classes estão no pacote `com.maria.papelaria.domain`, são mapeadas com JPA e preservam as regras de negócio. O Liquibase cria e versiona o esquema; o Hibernate apenas o valida. Os profiles `dev`, `test` e `prod` usam PostgreSQL, sem H2.

## API atual

Após iniciar a aplicação, estão disponíveis os cadastros, consultas por ID e listagens de:

- `http://localhost:8080/api/categorias`
- `http://localhost:8080/api/fornecedores`
- `http://localhost:8080/api/produtos`

Os contratos usam DTOs, validação de entrada e respostas de erro padronizadas.

### Exemplo de cadastro

```bash
curl -X POST http://localhost:8080/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nome":"Escolar"}'

curl -X POST http://localhost:8080/api/fornecedores \
  -H "Content-Type: application/json" \
  -d '{"razaoSocial":"Tilibra Papelaria Ltda","cnpj":"12345678000190"}'

curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "codigoBarras":"7890000000001",
    "descricao":"Caderno universitário 10 matérias",
    "saldoEstoque":20,
    "valorUnitario":18.90,
    "estoqueMinimo":5,
    "categoriaId":1,
    "fornecedorId":1
  }'
```

## Executando os testes

Os testes de persistência acessam `papelaria_test` e leem `DB_TEST_PASSWORD` do `.env` local. Os testes unitários de domínio não dependem de uma porta HTTP aberta.

```bash
./mvnw test
```

## Referência do curso

Material original das aulas: [jeffersonarpasserini/suporteos2026](https://github.com/jeffersonarpasserini/suporteos2026).
