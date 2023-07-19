package br.ufma.sppg.controller;

import java.util.*;

import br.ufma.sppg.dto.DocenteProducoes;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.DocenteProducoes;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

import java.util.List;


@RequestMapping("/api/docente")
@RestController
public class DocenteController{
    @Autowired
    TecnicaService tecnicaServivce;

    @Autowired
    ProducaoService producaoServivce;

    @Autowired
    OrientacaoService orientacaoServivce;

    @Autowired
    DocenteService docenteService;
    //teste de commit

    @GetMapping("/obterTodosDocentes")
    public ResponseEntity<?> obterTodosDocentes(){
        try{
            //obtem lista com todos os docentes
            List<Docente> docentes = docenteService.obterTodosDocentes();
            return ResponseEntity.ok(docentes);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesQualis/{anoIni}/{anoFim}")
    public ResponseEntity<?> obterProducoesQualis(@PathVariable(value = "anoIni", required = true)  Integer anoIni, @PathVariable(value = "anoFim", required = true)  Integer anoFim){
        try{
            List<DocenteProducoes> producoesDocente = producaoServivce.obterProducoesDocentes(anoIni, anoFim);
            List<DocenteProducoes> listaAux = new ArrayList<>();
            for (DocenteProducoes docenteProd : producoesDocente){
                List<Integer> qualis = new ArrayList<>(Collections.nCopies(9, 0));
                for (Producao producao : docenteProd.getProducoes()){
                    if (producao.getQualis() != null){
                        switch (producao.getQualis()) {
                            case "A1" -> qualis.set(0, qualis.get(0) + 1);
                            case "A2" -> qualis.set(1, qualis.get(1) + 1);
                            case "A3" -> qualis.set(2, qualis.get(2) + 1);
                            case "A4" -> qualis.set(3, qualis.get(3) + 1);
                            case "B1" -> qualis.set(4, qualis.get(4) + 1);
                            case "B2" -> qualis.set(5, qualis.get(5) + 1);
                            case "B3" -> qualis.set(6, qualis.get(6) + 1);
                            case "B4" -> qualis.set(7, qualis.get(7) + 1);
                            case "C" -> qualis.set(8, qualis.get(8) + 1);
                        }
                    }
                }
                DocenteProducoes ret = DocenteProducoes.builder().docente(docenteProd.getDocente()).qualis(qualis).build();
                listaAux.add(ret);
            }
            return ResponseEntity.ok(listaAux);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProdDocente/{idDocente}/{anoIni}/{anoFim}")
    public ResponseEntity<?> obterProdDocente(@PathVariable(value = "idDocente", required = true) Integer idDocente,
                                                    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
                                                    @PathVariable(value = "anoFim", required = true)  Integer anoFim){
        try{
            List<Producao> producoesDocente = producaoServivce.obterProducoesDocente(idDocente, anoIni, anoFim);
            return ResponseEntity.ok(producoesDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_orientacoes/{idPrograma}/{anoIni}/{anoFim}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "idPrograma", required = true) Integer idDocente,
                                                        @PathVariable(value = "anoIni", required = true)  Integer anoIni,
                                                        @PathVariable(value = "anoFim", required = true)  Integer anoFim){
        try{
            List<Orientacao> orientacaoDocente = orientacaoServivce.obterOrientacaoDocente(idDocente, anoIni, anoFim);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_tecnicas/{idDocente}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "idDocente", required = true) Integer idDocente){
        try{
            List<Tecnica> tecnicaDocente = tecnicaServivce.obterTecnicasDocente(idDocente); 
            return ResponseEntity.ok(tecnicaDocente);
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
            List <Orientacao> orientacoes = docenteService.obterOrientacoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity<>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasDocente/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTecnicasDocente(@PathVariable(value = "idDocente", required = true) Integer idDocente,
                                                    @PathVariable(value = "anoIni", required = true) Integer anoIni,
                                                    @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Tecnica> tecnicas = docenteService.obterTecnicasDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity<>(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesDocente/{idDocente}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterProducoesDocente(@PathVariable(value = "idDocente", required = true) Integer idDocente,
                                                    @PathVariable(value = "anoIni", required = true) Integer anoIni,
                                                    @PathVariable(value = "anoFin", required = true) Integer anoFin){
        try{
            List <Producao> producoes = docenteService.obterProducoesDocente(idDocente, anoIni, anoFin);
            return new ResponseEntity<>(producoes, HttpStatus.OK);
        } catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}