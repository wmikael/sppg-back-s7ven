package br.ufma.sppg.controller;

import br.ufma.sppg.dto.DocenteProducoes;
import br.ufma.sppg.service.ProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;

import java.util.List;

@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService servicePPG;

    @Autowired
    ProgramaService programa;

    @Autowired
    ProducaoService producao;

    @GetMapping("/all-programas")
    public ResponseEntity<List<Programa>> allProgramas(){
        var response=programa.allProgramas();
        if(response!=null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/obterPrograma")
    public ResponseEntity obterPrograma(
            @RequestParam("programa") String nome){
        try{
            List <Programa> programas = programa.obterPrograma(nome);
            return new ResponseEntity(programas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentesPrograma/{idPrograma}")
    public ResponseEntity obterDocentesPrograma(
            @RequestParam("docente") Integer idPrograma){
        try{
            List <Docente> docentes = programa.obterDocentesPrograma(idPrograma);
            return new ResponseEntity(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTodasAsProduções/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTodasAsProducoes(
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List<DocenteProducoes> producoes = producao.obterProducoesDocentes(anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesPrograma/{idPrograma}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterProducoesPrograma(
            @PathVariable(value = "idPrograma", required = true) Integer idPrograma,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Producao> producoes = programa.obterProducoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesDocente/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterOrientacoesDocente(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Orientacao> orientacoes = programa.obterOrientacoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity<>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasDocente/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTecnicasDocente(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
            try{
                List <Tecnica> tecnicas = programa.obterTecnicasDocente(idDocente, anoIni, anoFin);
                return new ResponseEntity<>(tecnicas, HttpStatus.OK);
            }catch (ServicoRuntimeException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
    }

    @GetMapping("/obterProducoesDocente/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterProducoesDocente(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Producao> producoes = programa.obterProducoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity<>(producoes, HttpStatus.OK);
        } catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesPrograma/{idPrograma}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterOrientacoesPorgrama(
            @PathVariable(value = "idPrograma", required = true) Integer idPrograma,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Orientacao> orientacoes = programa.obterOrientacoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPrograma/{idPrograma}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTecnicasPrograma(
            @PathVariable(value = "idPrograma", required = true) Integer idPrograma,
            @PathVariable(value = "anoIni", required = true) Integer anoIni,
            @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Tecnica> tecnicas = programa.obterTecnicas(idPrograma, anoIni, anoFin);
            return new ResponseEntity(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    ////////////////

    @GetMapping("/qtv_orientacoes_producao") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoProducao(
            @RequestParam("idPrograma") Integer idPrograma,
            @RequestParam("anoInicial") Integer anoIni,
            @RequestParam("anoFinal") Integer aniFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoProducao(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/qtv_orientacoes_tecnica") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoTecnica(
            @RequestParam("idPrograma") Integer idPrograma,
            @RequestParam("anoInicial") Integer anoIni,
            @RequestParam("anoFinal") Integer aniFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoTecnica(idPrograma, anoIni, aniFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
