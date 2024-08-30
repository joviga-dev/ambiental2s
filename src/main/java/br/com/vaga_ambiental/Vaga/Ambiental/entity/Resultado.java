package br.com.vaga_ambiental.Vaga.Ambiental.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Resultado {
    public List<Cidade> resultado = new ArrayList<>();
}
