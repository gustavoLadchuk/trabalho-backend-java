package br.grupointegrado.educacional.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Turmas")
public class Turma {

    @Id
    private int id;
    @Column
    private int ano;
    @Column
    private int semestre;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
