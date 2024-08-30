package br.com.vaga_ambiental.Vaga.Ambiental.service;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vaga_ambiental.Vaga.Ambiental.entity.Cidade;
import br.com.vaga_ambiental.Vaga.Ambiental.entity.Feriado;
import lombok.Data;

@Service
@Data
public class WebScraperService {
    private WebDriver driver;
    private final String url = "https://www.feriados.com.br";
    public List<Feriado> lstFeriados = new ArrayList<>();
    public List<Cidade> lstCidades = new ArrayList<>();
    @Autowired
    public FeriadoService feriadoService;

    public void coletaEArmazenaInformacoes() {

        System.out.println("Coletando informações do site...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);

        driver.get(url);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        int index = 0;

        for (Cidade cidade : this.lstCidades) {

            WebElement menuEstado = driver.findElement(By.name("estado"));
            menuEstado.click();
            menuEstado.findElement(By.xpath("//option[@value='" + cidade.getEstado() + "']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cidade")));

            WebElement menuCidade = driver.findElement(By.name("cidade"));
            menuCidade.click();
            menuCidade
                    .findElement(By.xpath("//option[@value='" + removerAcentos(cidade.getCidade()).toUpperCase() + "']"))
                    .click();

            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(
                            By.xpath("//div[@id='Feriados " + cidade.getCidade().toUpperCase() + " 2024']")));

            WebElement tabelaFeriados = driver
                    .findElement(By.xpath("//div[@id='Feriados " + cidade.getCidade().toUpperCase() + " 2024']"));

            List<WebElement> feriados = tabelaFeriados.findElements(By.tagName("li"));

            driver.switchTo().frame("calendar_frame");

            List<WebElement> elementFeriadosNacionais = driver
                    .findElements(By.cssSelector("[class='feriado_nacional']"));

            List<WebElement> elementFeriadosMunicipais = driver
                    .findElements(By.cssSelector("[class='feriado_municipal']"));

            List<String> datasFeriadosNacionais = new ArrayList<>();
            for (WebElement feriado : elementFeriadosNacionais) {
                WebElement dataElement = feriado.findElement(By.tagName("div"));
                LocalDate data = LocalDate.parse(dataElement.getAttribute("id").substring(4),DateTimeFormatter.ofPattern("yyyy-M-d"));
                datasFeriadosNacionais.add(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            List<String> datasFeriadosMunicipais = new ArrayList<>();
            for (WebElement feriado : elementFeriadosMunicipais) {
                WebElement dataElement = feriado.findElement(By.tagName("div"));
                LocalDate data = LocalDate.parse(dataElement.getAttribute("id").substring(4),DateTimeFormatter.ofPattern("yyyy-M-d"));
                datasFeriadosMunicipais.add(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            driver.switchTo().defaultContent();

            for (WebElement feriado : feriados) {
                String data = feriado.getText().substring(0, 10);
                String nome = feriado.getText().substring(13);
                String tipo = "";
                
                if (datasFeriadosNacionais.contains(data)) {
                    tipo = "NACIONAL";
                } else if (datasFeriadosMunicipais.contains(data)) {
                    tipo = "MUNICIPAL";
                } else {
                    tipo = "";
                }

                this.lstFeriados.add(new Feriado(LocalDate.parse(data,DateTimeFormatter.ofPattern("dd/MM/yyyy")), nome, tipo, cidade));
            }

            System.out.println("Armazenando feriados da cidade " + cidade.getCidade());
            
            this.feriadoService.salvarFeriados(this.lstFeriados);

            this.lstCidades.get(index).setFeriados(this.lstFeriados);
            
            index++;
            this.lstFeriados.clear();
        }

        driver.quit();

        System.out.println("Informações coletadas com sucesso!");
    }

    public String removerAcentos(String texto) {
        String normalizado = Normalizer.normalize(texto, Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizado).replaceAll("");
    }

}
