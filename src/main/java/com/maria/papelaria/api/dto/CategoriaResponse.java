package com.maria.papelaria.api.dto;

import com.maria.papelaria.domain.Status;

public record CategoriaResponse(
        Long id,
        String nome,
        Status status) {
}
