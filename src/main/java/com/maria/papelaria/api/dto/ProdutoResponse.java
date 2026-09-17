package com.maria.papelaria.api.dto;

import com.maria.papelaria.domain.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoResponse(
        Long id,
        String codigoBarras,
        String descricao,
        BigDecimal saldoEstoque,
        BigDecimal valorUnitario,
        BigDecimal estoqueMinimo,
        BigDecimal valorEstoque,
        LocalDate dataCadastro,
        Status status,
        Long categoriaId,
        String categoriaNome,
        Long fornecedorId,
        String fornecedorRazaoSocial) {
}
