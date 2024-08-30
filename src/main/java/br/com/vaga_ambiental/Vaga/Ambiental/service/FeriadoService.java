package br.com.vaga_ambiental.Vaga.Ambiental.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Feriado;
import br.com.vaga_ambiental.Vaga.Ambiental.repository.FeriadoRepository;
import lombok.Data;

@Service
@Data
public class FeriadoService {
    private FeriadoRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(FeriadoService.class);
    
    @Autowired
    public FeriadoService(FeriadoRepository repository) {
        this.repository = repository;
    }

    public void salvarFeriados(List<Feriado> feriados) {
        try {
            this.repository.saveAll(feriados);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Erro ao salvar cidade: {}", e.getMessage());
        }
    }
}
