package com.maria.papelaria.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoriaTest {

    @Test
    void deveAdicionarProdutoEManejarOsDoisLadosDaAssociacao() {
        Categoria categoria = new Categoria("Escolar");
        Produto produto = novoProduto("7890000000001");

        categoria.adicionarProduto(produto);

        assertEquals(1, categoria.getProdutos().size());
        assertSame(produto, categoria.getProdutos().getFirst());
        assertSame(categoria, produto.getCategoria());
    }

    @Test
    void naoDeveAdicionarProdutoNulo() {
        Categoria categoria = new Categoria("Escolar");

        assertThrows(NullPointerException.class, () -> categoria.adicionarProduto(null));
    }

    @Test
    void naoDeveAdicionarDoisProdutosComOMesmoCodigo() {
        Categoria categoria = new Categoria("Escolar");
        categoria.adicionarProduto(novoProduto("7890000000001"));

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> categoria.adicionarProduto(novoProduto("7890000000001")));

        assertEquals("Código de barras já utilizado na categoria", excecao.getMessage());
    }

    @Test
    void naoDevePermitirQueProdutoPertençaADuasCategorias() {
        Categoria escolar = new Categoria("Escolar");
        Categoria escritorio = new Categoria("Escritório");
        Produto produto = novoProduto("7890000000001");
        escolar.adicionarProduto(produto);

        IllegalStateException excecao = assertThrows(
                IllegalStateException.class,
                () -> escritorio.adicionarProduto(produto));

        assertEquals("Produto já pertence a outra categoria", excecao.getMessage());
    }

    @Test
    void naoDeveExporUmaListaInternaModificavel() {
        Categoria categoria = new Categoria("Escolar");
        Produto produto = novoProduto("7890000000001");
        categoria.adicionarProduto(produto);

        assertThrows(
                UnsupportedOperationException.class,
                () -> categoria.getProdutos().add(novoProduto("7890000000002")));
    }

    private Produto novoProduto(String codigoBarras) {
        return new Produto(
                codigoBarras,
                "Caderno universitário 10 matérias",
                new BigDecimal("3.000"),
                new BigDecimal("18.90"),
                LocalDate.of(2026, 8, 20));
    }
}
