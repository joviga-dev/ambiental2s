package br.com.vaga_ambiental.Vaga.Ambiental.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Cidade;
import br.com.vaga_ambiental.Vaga.Ambiental.entity.Resultado;
import jakarta.transaction.Transactional;

@Service
public class ApiService {

    private static final String API_URL = "https://spprev.ambientalqvt.com.br/api/dinamico/avaliacao-vaga/registrar-feriados";
    private static final String token = "d88ce41e-ade7-4f65-b0da-b028e4bf4b77";
    @Autowired
    private CidadeService cidadeService;

    @Transactional
    public void enviarInformacoes(){
        try {
        HttpClient client = HttpClient.newHttpClient();

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        List<Cidade> cidades = cidadeService.listarCidades();

        Resultado resultado = new Resultado(cidades);

        String json = objectMapper.writeValueAsString(resultado);

        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Dados enviados com sucesso!");
        } else {
            System.out.println("Falha ao enviar dados. Código de status: " + response.statusCode());
        }
        
        System.out.println("Resposta: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
