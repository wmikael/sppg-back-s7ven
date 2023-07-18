package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;

    public List<Docente> obterTodosDocentes(){
        return repository.findAll();
    }

    public Indice obterIndice(Integer idDocente, Integer anoIni, Integer anoFin){ 
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = new ArrayList<>();

        producoes = repository.obterProducoes(idDocente, anoIni, anoFin);

            for(Producao producao : producoes){
                    
                switch (producao.getQualis()) {
                    case "A1":
                        iRestrito += 1.0f;
                        break;
                            
                    case "A2":
                        iRestrito += 0.85;
                        break;

                    case "A3":
                        iRestrito += 0.725;
                        break;

                    case "A4":
                        iRestrito += 0.625;
                        break;

                    case "B1":
                        iNRestrito += 0.5;
                        break;

                    case "B2":
                        iNRestrito += 0.25;
                        break;

                    case "B3":
                        iNRestrito += 0.1;
                        break;
                        
                    case "B4":
                        iNRestrito += 0.05;
                        break;
                    
                    default:
                        throw new ServicoRuntimeException("Uma das produções possui o Qualis inválido");
                    }
            }

        iGeral = iRestrito + iNRestrito;

        return new Indice(iRestrito, iNRestrito, iGeral);
    }

    public List<Producao> obterProducoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterProducoes(idDocente, anoIni, anoFin);

    }

    public List<Orientacao> obterOrientacoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterOrientacoes(idDocente, anoIni, anoFin);

    }

    public List<Tecnica> obterTecnicas(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterTecnicas(idDocente, anoIni, anoFin);

    }

    @Transactional
    public Docente salvarDocente(Docente doc){
        verificarDocente(doc);

        return repository.save(doc);
    }

    public Optional<Docente> obterDocente(Integer idDocente){
        verificarId(idDocente);

        return repository.findById(idDocente);
    }

    public List<Docente> obterDocentesNome(String nome){
        verificarPalavra(nome, "Nome inválido");

        return repository.findByNome(nome);
    }

    private void verificarPalavra(String nome, String mensagem){
        if(nome == null){
            throw new ServicoRuntimeException(mensagem);
        }
        if(nome.trim().equals("")){
            throw new ServicoRuntimeException(mensagem);
        }
    }

    private void verificarId(Integer idDocente){
        verificarNumero(idDocente, "Id Inválido");
        if(!repository.existsById(idDocente)){
            throw new ServicoRuntimeException("Id do Docente não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2){
        verificarNumero(data1, "Data Inválida");
        verificarNumero(data2, "Data Inválida");
        if(data1 > data2){
            throw new ServicoRuntimeException("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero, String mensagem){
        if(numero == null){
            throw new ServicoRuntimeException(mensagem);
        }

    }

    private void verificarDocente(Docente docente){
        verificarPalavra(docente.getNome(), "Nome inválido");
        verificarPalavra(docente.getLattes(), "Lattes inválido");
        verificarNumero(docente.getId(), "Id inválido");
        if(repository.existsById(docente.getId())){
            throw new ServicoRuntimeException("Id já registrado");
        }
        /*
        if(docente.getOrientacoes() == null){
            throw new ServicoRuntimeException("Lista de orientações inexistente");
        }
        if(docente.getTecnicas() == null){
            throw new ServicoRuntimeException("Lista de tecnicas inexistente");
        }
        if(docente.getProducoes() == null){
            throw new ServicoRuntimeException("Lista de produções inexistente");
        }
        if(docente.getProgramas() == null){
            throw new ServicoRuntimeException("Lista de programas inexistente");
        }
        */
    }
    public List<Orientacao> obterOrientacoesDocente(Integer idDocente, Integer anoIni, Integer anoFin) {
        verificarData(anoIni, anoFin);
        Docente docente = repository.findById(idDocente).orElseThrow(() -> new ServicoRuntimeException("Docente não encontrado"));
        List<Orientacao> orientacoes = new ArrayList<>();
        List<Orientacao> orientacoesDoc = new ArrayList<>();
        ArrayList<Integer> idOrientacoes = new ArrayList<>();

        orientacoesDoc = docente.getOrientacoes();
        for (Orientacao orientacao : orientacoesDoc) {

            if (orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin
                    && !idOrientacoes.contains(orientacao.getId())) {

                idOrientacoes.add(orientacao.getId());
                orientacoes.add(orientacao);
            }
        }

        return orientacoes;
    }

    public List<Producao> obterProducoesDocente(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarData(anoIni, anoFin);
        Docente docente = repository.findById(idDocente).orElseThrow(() -> new ServicoRuntimeException("Docente não encontrado"));
        List<Producao> producoes = new ArrayList<>();
        List<Producao> producoesDoc = new ArrayList<>();
        ArrayList<Integer> idProducoes = new ArrayList<>();

        producoesDoc = docente.getProducoes();
        for (Producao producao : producoesDoc) {

            if (producao.getAno() >= anoIni && producao.getAno() <= anoFin
                    && !idProducoes.contains(producao.getId())) {

                idProducoes.add(producao.getId());
                producoes.add(producao);
            }
        }
        return producoes;
    }

    public List<Tecnica> obterTecnicasDocente(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarData(anoIni, anoFin);
        Docente docente = repository.findById(idDocente).orElseThrow(() -> new ServicoRuntimeException("Docente não encontrado"));
        List<Tecnica> tecnicas= new ArrayList<>();
        List<Tecnica> tecnicasDoc = new ArrayList<>();
        ArrayList<Integer> idTecnicas = new ArrayList<>();

        tecnicasDoc = docente.getTecnicas();
        for (Tecnica tecnica : tecnicasDoc) {

            if (tecnica.getAno() >= anoIni && tecnica.getAno() <= anoFin
                    && !idTecnicas.contains(tecnica.getId())) {

                idTecnicas.add(tecnica.getId());
                tecnicas.add(tecnica);
            }
        }
        return tecnicas;
    }
}