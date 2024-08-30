package br.com.vaga_ambiental.Vaga.Ambiental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Cidade;
import br.com.vaga_ambiental.Vaga.Ambiental.repository.CidadeRepository;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository repository;

    public List<Cidade> listarCidades() {
        return repository.findAll();
    }
}
