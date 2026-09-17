package com.maria.papelaria.api.mapper;

import com.maria.papelaria.api.dto.CategoriaResponse;
import com.maria.papelaria.domain.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getStatus());
    }
}
