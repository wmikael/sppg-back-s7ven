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
            List<Docente> docentes = docenteService.obterTodosDocentes();
            return ResponseEntity.ok(docentes);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesQualis/{data1}/{data2}")
    public ResponseEntity<?> obterProducoesDeDocenteContadas(@PathVariable(value = "data1", required = true)  Integer data1,
                                                             @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<DocenteProducoes> producaoDocente = producaoServivce.obterProducoesDocentes(data1, data2);
            List<DocenteProducoes> listaAux = new ArrayList<>();
            for (DocenteProducoes docenteProducao : producaoDocente){
                List<Integer> qualis = new ArrayList<>(Collections.nCopies(9, 0));
                for (Producao producao : docenteProducao.getProducoes()){
                    if (producao.getQualis() != null){
                        if(producao.getQualis().equals("A1")){
                            qualis.set(0, qualis.get(0) + 1);
                        }else if(producao.getQualis().equals("A2")){
                            qualis.set(1, qualis.get(1) + 1);
                        }else if(producao.getQualis().equals("A3")){
                            qualis.set(2, qualis.get(2) + 1);
                        }else if(producao.getQualis().equals("A4")){
                            qualis.set(3, qualis.get(3) + 1);
                        }else if(producao.getQualis().equals("B1")){
                            qualis.set(4, qualis.get(4) + 1);
                        }else if(producao.getQualis().equals("B2")){
                            qualis.set(5, qualis.get(5) + 1);
                        }else if(producao.getQualis().equals("B3")){
                            qualis.set(6, qualis.get(6) + 1);
                        }else if(producao.getQualis().equals("B4")){
                            qualis.set(7, qualis.get(7) + 1);
                        }else if(producao.getQualis().equals("C")){
                            qualis.set(8, qualis.get(8) + 1);
                        }
                    }
                }
                DocenteProducoes returnObject = DocenteProducoes.builder().docente(docenteProducao.getDocente()).qualis(qualis).build();
                listaAux.add(returnObject);
            }
            return ResponseEntity.ok(listaAux);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_producoes/{id}/{data1}/{data2}")
    public ResponseEntity<?> obterProducoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Producao> producaoDocente = producaoServivce.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(producaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_orientacoes/{id}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Orientacao> orientacaoDocente = orientacaoServivce.obterOrientacaoDocente(idDocente, data1, data2);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_tecnicas/{id}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "id", required = true) Integer idDocente){

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
    public ResponseEntity<?> obterTecnicasDocente(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
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
    public ResponseEntity<?> obterProducoesDocente(
            @PathVariable(value = "idDocente", required = true) Integer idDocente,
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