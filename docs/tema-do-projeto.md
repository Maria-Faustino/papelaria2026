# Tema do projeto Papelaria

## Identificação

- Nome: `papelaria`
- Tema: controle de estoque de uma papelaria
- Objetivo: cadastrar produtos de papelaria e organizá-los por categorias, com fornecedor opcional

## Correspondência com o projeto de referência

O [suporteos2026](https://github.com/jeffersonarpasserini/suporteos2026) usa estoque genérico. Este projeto mantém a mesma estrutura conceitual no domínio de uma papelaria:

| Referência | Papelaria |
|---|---|
| `GrupoProduto` | `Categoria` |
| `Produto` | `Produto` |
| `Fornecedor` | `Fornecedor` |
| código de barras | código de barras |
| saldo em estoque | saldo em estoque |
| valor unitário | valor unitário |
| valor do estoque | saldo × valor unitário |
| estoque mínimo | estoque mínimo |
| CNPJ | CNPJ |

## Entidade de classificação

- Nome no singular: `Categoria`
- Nome no plural: categorias
- Descrição: classificação utilizada para organizar os itens da papelaria
- Exemplos: Escolar, Escritório, Arte, Embalagem
- Status: ativo ou inativo

## Entidade principal

- Nome no singular: `Produto`
- Nome no plural: produtos
- Código único: código de barras
- Descrição: nome comercial do item (caderno, caneta, lápis, cola)
- Medida quantitativa: saldo em estoque
- Valor monetário: valor unitário
- Valor calculado: saldo em estoque multiplicado pelo valor unitário
- Data relevante: data de cadastro
- Status: ativo ou inativo

## Entidade relacionada

- Nome no singular: `Fornecedor`
- Nome no plural: fornecedores
- Código único: CNPJ com 14 dígitos
- Descrição: fabricante ou distribuidor do item (Tilibra, BIC, Faber-Castell)
- Relacionamento: um fornecedor pode estar associado a vários produtos; o fornecedor de um produto é opcional

## Relacionamento

```text
Categoria  1 ─────── N Produto
Fornecedor 0..1 ─── N Produto
```

- Uma categoria pode classificar vários produtos.
- Cada produto pertence a uma categoria.
- O fornecedor associado a um produto é opcional para preservar registros anteriores.

## Exemplos

| Categoria | Código | Produto | Saldo | Valor unitário |
|---|---|---|---:|---:|
| Escolar | `7890000000001` | Caderno universitário 10 matérias | 20 | 18,90 |
| Escritório | `7890000000002` | Caneta esferográfica azul | 50 | 2,50 |
| Arte | `7890000000003` | Giz de cera 12 cores | 15 | 12,00 |
