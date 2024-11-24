package br.grupointegrado.educacional.dto;

import java.math.BigDecimal;

public record NotaRequestDTO(Integer matricula_id, Integer disciplina_id, BigDecimal nota) {
}
