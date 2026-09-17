# Postman — Papelaria API

## Importar

1. Abra o Postman → **Import**
2. Selecione:
   - `Papelaria.postman_collection.json`
   - `Papelaria.postman_environment.json`
3. No canto superior direito, escolha o environment **Papelaria Local**

## Antes de testar

1. No pgAdmin, execute o script `setup-pgadmin.sql` (senha: `postdba`)
2. Suba a API com profile `dev`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Ordem das requisições

1. **GET Health** — deve retornar `OK`
2. **POST Cadastrar Categoria**
3. **POST Cadastrar Fornecedor**
4. **POST Cadastrar Produto**
5. Listagens e buscas por ID

Os POSTs salvam automaticamente `categoriaId`, `fornecedorId` e `produtoId`.
