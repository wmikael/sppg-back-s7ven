package br.ufma.sppg.dto;

import java.util.List;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DocenteProducoes {
    Docente docente;
    List<Integer> qualis;
    List<Producao> producoes;
}
