package com.maria.papelaria.api.dto;

import com.maria.papelaria.domain.Status;

public record FornecedorResponse(
        Long id,
        String razaoSocial,
        String cnpj,
        Status status) {
}
