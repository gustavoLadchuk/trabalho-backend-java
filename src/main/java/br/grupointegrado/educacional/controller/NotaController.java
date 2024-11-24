package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.NotaRequestDTO;
import br.grupointegrado.educacional.model.Disciplina;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Nota;
import br.grupointegrado.educacional.repository.DisciplinaRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
import br.grupointegrado.educacional.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping
    public ResponseEntity<List<Nota>> findAll() {
        return ResponseEntity.ok(this.notaRepository.findAll());
    }

    @GetMapping("/matricula/{id}")
    public ResponseEntity<List<Nota>> findByMatriculaId(@PathVariable Integer id) {
        return ResponseEntity.ok(this.notaRepository.findByMatriculaId(id));
    }

    @GetMapping("/disciplina/{id}")
    public ResponseEntity<List<Nota>> findByDisciplinaID(@PathVariable Integer id) {
        return ResponseEntity.ok(this.notaRepository.findByDisciplinaId(id));
    }

    @PostMapping
    public ResponseEntity<Nota> save(@RequestBody NotaRequestDTO dto) {
        Nota nota = new Nota();

        Matricula matricula = this.matriculaRepository.findById(dto.matricula_id())
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));

        Disciplina disciplina = this.disciplinaRepository.findById(dto.disciplina_id())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        nota.setNota(dto.nota());
        nota.setMatricula(matricula);
        nota.setDisciplina(disciplina);
        nota.setData_lancamento(LocalDate.now());

        this.notaRepository.save(nota);

        return ResponseEntity.ok(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> update(@PathVariable Integer id, @RequestBody NotaRequestDTO dto) {
        Nota nota = this.notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));

        Matricula matricula = this.matriculaRepository.findById(dto.matricula_id())
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));

        Disciplina disciplina = this.disciplinaRepository.findById(dto.disciplina_id())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        nota.setNota(dto.nota());
        nota.setMatricula(matricula);
        nota.setDisciplina(disciplina);
        nota.setData_lancamento(LocalDate.now());

        this.notaRepository.save(nota);

        return ResponseEntity.ok(nota);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Nota nota = this.notaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada"));

        this.notaRepository.delete(nota);

        return ResponseEntity.noContent().build();
    }
}
