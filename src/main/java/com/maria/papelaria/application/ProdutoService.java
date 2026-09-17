package com.maria.papelaria.application;

import com.maria.papelaria.domain.Categoria;
import com.maria.papelaria.domain.Fornecedor;
import com.maria.papelaria.domain.Produto;
import com.maria.papelaria.repository.CategoriaRepository;
import com.maria.papelaria.repository.FornecedorRepository;
import com.maria.papelaria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaRepository categoriaRepository,
            FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Produto cadastrar(Produto produto, Long categoriaId, Long fornecedorId) {
        if (produtoRepository.existsByCodigoBarras(produto.getCodigoBarras())) {
            throw new RecursoDuplicadoException("Código de barras já cadastrado");
        }

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada"));
        categoria.adicionarProduto(produto);

        if (fornecedorId != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Fornecedor não encontrado"));
            produto.associarFornecedor(fornecedor);
        }

        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.buscarPorIdComRelacionamentos(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Produto> listar() {
        return produtoRepository.buscarTodosComRelacionamentos();
    }

    @Transactional
    public Produto receberEstoque(Long id, BigDecimal quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado"));
        produto.receberEstoque(quantidade);
        return produto;
    }
}
