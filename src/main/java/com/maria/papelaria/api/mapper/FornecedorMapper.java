package com.maria.papelaria.api.mapper;

import com.maria.papelaria.api.dto.FornecedorRequest;
import com.maria.papelaria.api.dto.FornecedorResponse;
import com.maria.papelaria.domain.Fornecedor;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper {

    public Fornecedor toEntity(FornecedorRequest request) {
        return new Fornecedor(request.razaoSocial(), request.cnpj());
    }

    public FornecedorResponse toResponse(Fornecedor fornecedor) {
        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getStatus());
    }
}
