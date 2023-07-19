package br.ufma.sppg.controller;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnica")
public class TecnicaController {

    @Autowired
    TecnicaService tecnicaService;

    @GetMapping("/obterTodasTecnicas")
    public ResponseEntity<?> obterTodasTecnicas(){
        try{
            List<Tecnica> tecnicas = tecnicaService.obterTodasTecnicas();
            return ResponseEntity.ok(tecnicas);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPorDocente/{idDocente}")
    public ResponseEntity<?> obterTecnicasPorDocente(@PathVariable(value = "idDocente", required = true)  Integer idDocente) {
        try {
            List<Tecnica> tecnicas = tecnicaService.obterTecnicasDocente(idDocente);
            return ResponseEntity.ok(tecnicas);
        } catch (ServicoRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/atualizaEstatisticasTecnica")
    public ResponseEntity<?> atualizaEstatisticasProducao(
            @RequestParam(value = "idTecnica", required = true) Integer idTecnica,
            @RequestParam(value = "qtd_grad", required = true) Integer qtd_grad,
            @RequestParam(value = "qtd_mest", required = true) Integer qtd_mest,
            @RequestParam(value = "qtd_dout", required = true) Integer qtd_dout){
        try{
            Tecnica tecnica= tecnicaService.atualizarEstatisticas(idTecnica, qtd_grad, qtd_mest, qtd_dout);
            return new ResponseEntity<>(tecnica, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
