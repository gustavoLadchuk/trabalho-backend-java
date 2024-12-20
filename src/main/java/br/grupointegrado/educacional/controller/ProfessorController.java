package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.ProfessorRequestDTO;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    private void checkEmail(String email){
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private void checkTelefone(String matricula){
        if (!matricula.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
            throw new IllegalArgumentException("Telefone inválido, formato correto: (xx) xxxxx-xxxx");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Professor>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        return ResponseEntity.ok(professor);
    }

    @PostMapping
    public ResponseEntity<Professor> save(@RequestBody ProfessorRequestDTO dto) {
        Professor professor = new Professor();

        checkEmail(dto.email());
        checkTelefone(dto.telefone());

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        this.repository.save(professor);
        return ResponseEntity.ok(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable Integer id, @RequestBody ProfessorRequestDTO dto) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        checkEmail(dto.email());
        checkTelefone(dto.telefone());

        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setTelefone(dto.telefone());
        professor.setEspecialidade(dto.especialidade());

        this.repository.save(professor);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Professor professor = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        this.repository.delete(professor);
        return ResponseEntity.noContent().build();
    }

}
