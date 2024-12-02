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

    private void checkCodigo(String codigo){
        if (!codigo.matches("\\d+")) {
            throw new IllegalArgumentException("Código inválido");
        }
    }

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

        if (dto.carga_horaria() <= 0){
            throw new IllegalArgumentException("Carga horária inválida");
        }

        checkCodigo(dto.codigo());

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

        if (dto.carga_horaria() <= 0){
            throw new IllegalArgumentException("Carga horária inválida");
        }

        checkCodigo(dto.codigo());

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

    @PostMapping("/{id}/disciplinas")
    public ResponseEntity<Curso> saveDisciplina(@PathVariable Integer id, @RequestBody DisciplinaRequestDTO dto){
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Professor professor = this.professorRepository.findById(dto.professor_id())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        checkCodigo(dto.codigo());

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.nome());
        disciplina.setCodigo(dto.codigo());
        disciplina.setCurso(curso);
        disciplina.setProfessor(professor);

        this.disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/disciplinas/{id_disciplina}")
    public ResponseEntity<Curso> updateDisciplina(@PathVariable Integer id,
                                                  @PathVariable Integer id_disciplina,
                                                  @RequestBody DisciplinaRequestDTO dto) {
        Curso curso = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Disciplina disciplina = this.disciplinaRepository.findById(id_disciplina)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        Professor professor = this.professorRepository.findById(dto.professor_id())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        checkCodigo(dto.codigo());

        disciplina.setCurso(curso);
        disciplina.setProfessor(professor);
        disciplina.setCodigo(dto.codigo());
        disciplina.setNome(dto.nome());

        this.disciplinaRepository.save(disciplina);
        return ResponseEntity.ok(curso);

    }

    @DeleteMapping("/delete-disciplina/{id_disciplina}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Integer id_disciplina) {

        Disciplina disciplina = this.disciplinaRepository.findById(id_disciplina)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        this.disciplinaRepository.delete(disciplina);
        return ResponseEntity.noContent().build();
    }



}
