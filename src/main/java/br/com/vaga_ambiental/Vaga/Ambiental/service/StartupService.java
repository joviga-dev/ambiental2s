package br.com.vaga_ambiental.Vaga.Ambiental.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StartupService {

    private final ExcelService excelService;
    private final WebScraperService webScraperService;
    private final ApiService apiService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        excelService.lerExcel();
        webScraperService.setLstCidades(excelService.getCidades());
        webScraperService.coletaEArmazenaInformacoes();
        apiService.enviarInformacoes();
    }
}
