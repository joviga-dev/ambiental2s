package br.com.vaga_ambiental.Vaga.Ambiental.entity;

import java.time.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Feriado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private LocalDate data;
    private String feriado;
    private String tipo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cidade")
    @JsonBackReference
    public Cidade cidade;

    public Feriado(LocalDate data, String feriado, String tipo, Cidade cidade) {
        this.data = data;
        this.feriado = feriado;
        this.tipo = tipo;
        this.cidade = cidade;
    }
}
