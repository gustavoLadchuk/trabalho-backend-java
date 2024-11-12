package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.ProfessorRequestDTO;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    @GetMapping()
    public List<Professor> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Professor findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
    }

    @PostMapping
    public Professor save(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = new Professor();

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        return this.repository.save(professor);
    }

    @PutMapping("/{id}")
    public Professor update(@PathVariable Integer id, @RequestBody ProfessorRequestDTO dto) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        return this.repository.save(professor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        this.repository.delete(professor);
    }

}
