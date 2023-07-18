package br.ufma.sppg.controller;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
