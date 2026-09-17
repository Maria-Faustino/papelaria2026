package com.maria.papelaria.application;

import com.maria.papelaria.domain.Categoria;
import com.maria.papelaria.domain.Fornecedor;
import com.maria.papelaria.domain.Produto;
import com.maria.papelaria.repository.CategoriaRepository;
import com.maria.papelaria.repository.FornecedorRepository;
import com.maria.papelaria.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void deveCadastrarProdutoComCategoriaEFornecedor() {
        Categoria categoria = categoriaRepository.save(new Categoria("Escolar de teste"));
        Fornecedor fornecedor = fornecedorRepository.save(new Fornecedor(
                "Tilibra de teste",
                "11111111000191"));

        Produto cadastrado = produtoService.cadastrar(
                novoProduto("TESTE-SERVICE-001"),
                categoria.getId(),
                fornecedor.getId());

        assertNotNull(cadastrado.getId());
        assertEquals(categoria.getId(), cadastrado.getCategoria().getId());
        assertEquals(fornecedor.getId(), cadastrado.getFornecedor().getId());
    }

    @Test
    void deveImpedirCodigoDeBarrasDuplicado() {
        Categoria categoria = categoriaRepository.save(new Categoria("Categoria duplicidade"));
        produtoService.cadastrar(novoProduto("TESTE-DUPLICADO"), categoria.getId(), null);

        assertThrows(
                RecursoDuplicadoException.class,
                () -> produtoService.cadastrar(
                        novoProduto("TESTE-DUPLICADO"),
                        categoria.getId(),
                        null));
    }

    @Test
    void deveInformarCategoriaInexistenteSemSalvarProduto() {
        Produto produto = novoProduto("TESTE-SEM-CATEGORIA");

        assertThrows(
                RecursoNaoEncontradoException.class,
                () -> produtoService.cadastrar(produto, Long.MAX_VALUE, null));
        assertEquals(false, produtoRepository.existsByCodigoBarras("TESTE-SEM-CATEGORIA"));
    }

    @Test
    void deveAtualizarSaldoPorDirtyChecking() {
        Categoria categoria = categoriaRepository.save(new Categoria("Categoria dirty checking"));
        Produto produto = produtoService.cadastrar(
                novoProduto("TESTE-DIRTY-CHECKING"),
                categoria.getId(),
                null);

        produtoService.receberEstoque(produto.getId(), new BigDecimal("5.000"));

        Produto atualizado = produtoRepository.findById(produto.getId()).orElseThrow();
        assertEquals(0, new BigDecimal("15.000").compareTo(atualizado.getSaldoEstoque()));
    }

    private Produto novoProduto(String codigo) {
        return new Produto(
                codigo,
                "Caneta esferográfica azul",
                new BigDecimal("10.000"),
                new BigDecimal("2.50"),
                new BigDecimal("2.000"),
                LocalDate.of(2026, 8, 27));
    }
}
