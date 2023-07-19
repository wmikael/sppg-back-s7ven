package br.ufma.sppg.controller;

import br.ufma.sppg.model.Producao;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producao")
public class ProducaoController {

    @Autowired
    ProducaoService producaoService;

    @GetMapping("/obterTodasAsProduções/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTodasAsProducoes(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List<Producao> producoes = producaoService.obterProducoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizaEstatisticasProducao")
    public ResponseEntity<?> atualizaEstatisticasProducao(
            @RequestParam(value = "idPrograma", required = true) Integer idPrograma,
            @RequestParam(value = "qtd_grad", required = true) Integer qtd_grad,
            @RequestParam(value = "qtd_mest", required = true) Integer qtd_mest,
            @RequestParam(value = "qtd_dout", required = true) Integer qtd_dout){
        try{
            Producao producao = producaoService.atualizarEstatisticas(idPrograma, qtd_grad, qtd_mest, qtd_dout);
            return new ResponseEntity<>(producao, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @PutMapping("/atualizaOrientacoesProducoes")
//    public ResponseEntity<?> atualizaOrientacoesProducoes(
//            @RequestParam(value = "idOrientacao", required = true) List<Integer> idOrientacao,
//            @RequestParam(value = "idProducao", required = true) Integer idProducao){
//          try{
//              Producao producao = producaoService.atualizarOrientacoes(idOrientacao, idProducao);
//              return new ResponseEntity<>(producao, HttpStatus.OK);
//          }catch (ServicoRuntimeException e){
//              return ResponseEntity.badRequest().body(e.getMessage());
//          }
//    }

}
