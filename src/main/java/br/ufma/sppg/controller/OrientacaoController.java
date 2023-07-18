package br.ufma.sppg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@RequestMapping("/api/orientacao")
public class OrientacaoController {

  @Autowired
  OrientacaoService orientacaoService;

  @Autowired
  ProducaoService producaoService;

  @Autowired
  TecnicaService tecnicaService;

  @GetMapping("/obterOrientacoesDocenteComTecnica")
  public ResponseEntity<?> obterOrientacoesDocenteComTecnica(
      @RequestParam("docente") Integer idDocente, Integer dataInicio, Integer dataFim) {
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.obterOrientacoesComTecnicaPorPeriodo(idDocente,
          dataInicio, dataFim);
      return new ResponseEntity<>(
          orientacoes.get(),
          HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/associarOrientacaoProducao{idOri}/producao/{idProd}")
  public ResponseEntity<?> associarOrientacaoProducao(@PathVariable Integer idOri, @PathVariable Integer idProd) {
    try {
      Orientacao orientacao = orientacaoService.associarOrientacaoProducao(idOri, idProd);
      return ResponseEntity.ok(orientacao);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/obterOrientacoesDocenteComProducao/{idDocente}/{dataInicio}/{dataFim}")
  public ResponseEntity<?> obterOrientacaoDocenteComProducao(@PathVariable("idDocente") Integer idDocente,
      @PathVariable("dataInicio") Integer dataInicio, @PathVariable("dataFim") Integer dataFim) {
    try {
      Optional<List<Orientacao>> orientacoes = orientacaoService.obterOrientacoesComProducaoPorPeriodo(idDocente,
          dataInicio, dataFim);

      return new ResponseEntity<>(
          orientacoes,
          HttpStatus.OK);
    } catch (ServicoRuntimeException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}