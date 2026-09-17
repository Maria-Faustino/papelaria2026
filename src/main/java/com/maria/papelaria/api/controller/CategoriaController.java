package com.maria.papelaria.api.controller;

import com.maria.papelaria.api.dto.CategoriaRequest;
import com.maria.papelaria.api.dto.CategoriaResponse;
import com.maria.papelaria.api.mapper.CategoriaMapper;
import com.maria.papelaria.application.CategoriaService;
import com.maria.papelaria.domain.Categoria;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService service;
    private final CategoriaMapper mapper;

    public CategoriaController(CategoriaService service, CategoriaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> cadastrar(
            @Valid @RequestBody CategoriaRequest request) {
        Categoria categoria = service.cadastrar(request.nome());
        URI location = URI.create("/api/categorias/" + categoria.getId());
        return ResponseEntity.created(location).body(mapper.toResponse(categoria));
    }

    @GetMapping("/{id}")
    public CategoriaResponse buscarPorId(@PathVariable Long id) {
        return mapper.toResponse(service.buscarPorId(id));
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return service.listar().stream().map(mapper::toResponse).toList();
    }
}
