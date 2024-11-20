package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.TurmaRequestDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping()
    public ResponseEntity<List<Turma>> findAll() {

        return ResponseEntity.ok(this.turmaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Turma turma = this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        return ResponseEntity.ok(turma);
    }

    @GetMapping("/semestre/{semestre}")
    public ResponseEntity<List<Turma>> findBySemestre(@PathVariable Integer semestre){
        List<Turma> turmas = this.turmaRepository.findBySemestre(semestre);
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/curso/{curso_id}")
    public ResponseEntity<List<Turma>> findByCurso(@PathVariable Integer curso_id){
        List<Turma> turmas = this.turmaRepository.findByCursoId(curso_id);
        return ResponseEntity.ok(turmas);
    }

    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());
        turma.setCurso(curso);

        this.turmaRepository.save(turma);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());
        turma.setCurso(curso);

        this.turmaRepository.save(turma);
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Turma turma = this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        this.turmaRepository.delete(turma);
        return ResponseEntity.noContent().build();
    }

}
