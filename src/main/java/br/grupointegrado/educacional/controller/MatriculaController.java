package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.MatriculaRequestDTO;
import br.grupointegrado.educacional.model.Aluno;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.AlunoRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public  ResponseEntity<List<Matricula>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Integer id){
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matricula não encontrada"));

        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<Matricula> save(@RequestBody MatriculaRequestDTO dto){
        Matricula matricula = new Matricula();

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                    .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        Turma turma = this.turmaRepository.findById(dto.turma_id())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        long idadeAluno = ChronoUnit.YEARS.between(aluno.getData_nascimento(), LocalDate.now());

        if (idadeAluno < 18){
            throw new IllegalArgumentException("O aluno não possui a idade mínima para se matricular nesse curso");
        }

        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        this.repository.save(matricula);

        return ResponseEntity.ok(matricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Integer id, @RequestBody MatriculaRequestDTO dto){
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        Turma turma = this.turmaRepository.findById(dto.turma_id())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));

        long idadeAluno = ChronoUnit.YEARS.between(aluno.getData_nascimento(), LocalDate.now());

        if (idadeAluno < 18){
            throw new IllegalArgumentException("O aluno não possui a idade mínima para se matricular nesse curso");
        }

        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        this.repository.save(matricula);

        return ResponseEntity.ok(matricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));

        this.repository.delete(matricula);
        return ResponseEntity.noContent().build();
    }




}
