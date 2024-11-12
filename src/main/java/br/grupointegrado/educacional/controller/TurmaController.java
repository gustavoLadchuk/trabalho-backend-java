package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.TurmaRequestDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.CursoRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Turma> findAll() {
        return this.turmaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Turma findById(@PathVariable Integer id) {
        return this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
    }

    @PostMapping
    public Turma save(@RequestBody TurmaRequestDTO dto) {
        Turma turma = new Turma();

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());
        turma.setCurso(curso);

        return this.turmaRepository.save(turma);
    }

    @PutMapping("/{id}")
    public Turma update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        Curso curso = this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());
        turma.setCurso(curso);

        return this.turmaRepository.save(turma);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        Turma turma = this.turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
        this.turmaRepository.delete(turma);
    }

}
