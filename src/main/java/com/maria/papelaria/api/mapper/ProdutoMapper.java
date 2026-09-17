package com.maria.papelaria.api.mapper;

import com.maria.papelaria.api.dto.ProdutoRequest;
import com.maria.papelaria.api.dto.ProdutoResponse;
import com.maria.papelaria.domain.Fornecedor;
import com.maria.papelaria.domain.Produto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest request) {
        return new Produto(
                request.codigoBarras(),
                request.descricao(),
                request.saldoEstoque(),
                request.valorUnitario(),
                request.estoqueMinimo(),
                LocalDate.now());
    }

    public ProdutoResponse toResponse(Produto produto) {
        Fornecedor fornecedor = produto.getFornecedor();

        return new ProdutoResponse(
                produto.getId(),
                produto.getCodigoBarras(),
                produto.getDescricao(),
                produto.getSaldoEstoque(),
                produto.getValorUnitario(),
                produto.getEstoqueMinimo(),
                produto.calcularValorEstoque(),
                produto.getDataCadastro(),
                produto.getStatus(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                fornecedor == null ? null : fornecedor.getId(),
                fornecedor == null ? null : fornecedor.getRazaoSocial());
    }
}
