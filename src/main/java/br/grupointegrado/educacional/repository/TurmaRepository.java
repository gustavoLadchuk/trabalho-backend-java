package br.grupointegrado.educacional.repository;

import br.grupointegrado.educacional.model.Aluno;
import br.grupointegrado.educacional.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    List<Turma> findBySemestre(Integer semestre);

    List<Turma> findByCursoId(Integer curso_id);


}
