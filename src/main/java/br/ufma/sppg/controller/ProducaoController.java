package br.ufma.sppg.controller;

import br.ufma.sppg.dto.DocenteProducoes;
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
    ProducaoService producao;

    @GetMapping("/obterTodasAsProduções/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTodasAsProducoes(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List<Producao> producoes = producao.obterProducoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
