package com.maria.papelaria.application;

import com.maria.papelaria.domain.Categoria;
import com.maria.papelaria.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Categoria cadastrar(String nome) {
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new RecursoDuplicadoException("Nome da categoria já cadastrado");
        }
        return repository.save(new Categoria(nome));
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Categoria não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return repository.findAll();
    }
}
