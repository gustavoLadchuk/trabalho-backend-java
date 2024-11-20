package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.CursoRequestDTO;
import br.grupointegrado.educacional.dto.DisciplinaRequestDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Disciplina;
import br.grupointegrado.educacional.model.Professor;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.DisciplinaRepository;
import br.grupointegrado.educacional.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping()
    public ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody CursoRequestDTO dto) {
        Curso curso = new Curso();

        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        this.repository.save(curso);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody CursoRequestDTO dto) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        curso.setNome(dto.nome());
        curso.setCodigo(dto.codigo());
        curso.setCarga_horaria(dto.carga_horaria());

        this.repository.save(curso);
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        this.repository.delete(curso);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-disciplina")
    public ResponseEntity<Curso> addMatricula(@PathVariable Integer id, @RequestBody DisciplinaRequestDTO dto){
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Professor professor = this.professorRepository.findById(dto.professor_id())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());
        disciplina.setCurso(curso);
        disciplina.setProfessor(professor);

        this.disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(curso);
    }



}
