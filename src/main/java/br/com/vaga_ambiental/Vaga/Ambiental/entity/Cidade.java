package br.com.vaga_ambiental.Vaga.Ambiental.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    public String estado;
    @Column(unique = true, nullable = false)
    public String cidade;
    @OneToMany(mappedBy = "cidade")
    @JsonManagedReference
    public List<Feriado> feriados;

    public Cidade(String estado, String cidade) {
        this.estado = estado;
        this.cidade = cidade;
        this.feriados = new ArrayList<>();
    }
}
