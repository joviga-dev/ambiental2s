package br.com.vaga_ambiental.Vaga.Ambiental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
