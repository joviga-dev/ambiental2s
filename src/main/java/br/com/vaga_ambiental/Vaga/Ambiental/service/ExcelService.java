package br.com.vaga_ambiental.Vaga.Ambiental.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Cidade;
import lombok.Data;

@Data
@Service
public class ExcelService {

    private final String path = "/app/resources/Projeto-vaga-Ambiental.xlsx";
    public HashMap<String, String> mapCidadesEstados = new HashMap<>();
    public List<Cidade> cidades = new ArrayList<>();

    public void lerExcel() {
        System.out.println("Lendo excel...");
        try {
            File file = new File(path);
            try (Workbook workbook = new XSSFWorkbook(file)) {
                for (Sheet sheet : workbook) {
                    for (Row row : sheet) {
                        if(row.getRowNum() == 0) continue;
                        String estado = row.getCell(0).getStringCellValue();
                        String nomeCidade = row.getCell(1).getStringCellValue();                        
                        this.cidades.add(new Cidade(estado, nomeCidade));                   
                    }
                }
            }
            System.out.println("Informações lidas com sucesso");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
