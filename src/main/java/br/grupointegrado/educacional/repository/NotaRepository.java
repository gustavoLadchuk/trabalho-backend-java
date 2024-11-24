package br.grupointegrado.educacional.repository;

import br.grupointegrado.educacional.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

    List<Nota> findByMatriculaId(Integer matricula_id);

    List<Nota> findByDisciplinaId(Integer disciplina_id);

}
