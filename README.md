# RPA Feriados — Selenium

RPA desenvolvido em **Java + Spring Boot + Selenium** para automatizar a consulta de feriados municipais e nacionais a partir de uma lista de cidades e estados.

A aplicação realiza a leitura dos dados de entrada, automatiza a navegação no portal de feriados utilizando Selenium, armazena os resultados em **PostgreSQL** e disponibiliza o processamento para posterior integração com uma API REST.

## 🚀 Funcionalidades

* Leitura de cidades e estados a partir de dados de entrada
* Automação de navegador utilizando Selenium WebDriver
* Consulta de feriados municipais e nacionais
* Extração das datas e informações dos feriados
* Persistência dos dados em PostgreSQL
* Integração com API REST
* Execução containerizada utilizando Docker
* Configuração através de variáveis de ambiente

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada em **Spring Boot**, separando as responsabilidades de automação, persistência e integração externa.

```text
                    ┌─────────────────────┐
                    │     Dados de        │
                    │       entrada       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     Spring Boot     │
                    │      Application    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       Selenium      │
                    │    Web Automation   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │  Portal de Feriados │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     PostgreSQL      │
                    │    Persistência     │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      REST API       │
                    │     Integração      │
                    └─────────────────────┘
```

## 🛠️ Tecnologias

| Tecnologia         | Utilização                            |
| ------------------ | ------------------------------------- |
| Java               | Linguagem principal                   |
| Spring Boot        | Framework da aplicação                |
| Selenium WebDriver | Automação e navegação web             |
| PostgreSQL         | Persistência dos dados                |
| Maven              | Gerenciamento de dependências e build |
| Docker             | Containerização da aplicação          |
| Docker Compose     | Orquestração dos containers           |

## 🔄 Fluxo de execução

O processamento segue as seguintes etapas:

1. A aplicação recebe os dados de entrada contendo **estado e cidade**.
2. Os dados são processados pela aplicação Spring Boot.
3. O Selenium inicia o navegador e acessa o portal de feriados.
4. Para cada cidade, o RPA realiza a consulta correspondente.
5. Os feriados municipais e nacionais encontrados são extraídos.
6. Os dados coletados são persistidos no PostgreSQL.
7. Os registros armazenados são preparados no formato esperado pela API.
8. A aplicação envia os dados através de uma requisição HTTP `POST`.
9. O retorno da API é processado para identificar sucesso ou erro na operação.

## 🗄️ Persistência

Os dados coletados pelo RPA são armazenados em um banco **PostgreSQL**, permitindo que as informações obtidas durante a automação sejam persistidas antes da integração com o serviço externo.

Essa abordagem também permite separar o processo de **coleta** do processo de **envio**, evitando que uma falha na API externa resulte na perda dos dados coletados.

## 🌐 Integração com API

Após a coleta e persistência dos dados, a aplicação realiza uma requisição `POST` para a API configurada no projeto.

A URL da API é definida através de uma constante no código, permitindo alterar facilmente o endpoint conforme o ambiente ou serviço utilizado.

```java
private static final String API_URL =
        System.getenv().getOrDefault(
                "API_URL",
                "https://sua-api.com/api/registrar-feriados"
        );
```

> Para utilizar uma API diferente, basta alterar o valor da constante `API_URL`.

### Endpoint

```http
POST {API_URL}
```

### Autenticação

A integração utiliza autenticação através de **Bearer Token**.

```http
Authorization: Bearer <TOKEN>
```

### Payload

```json
{
  "estado": "São Paulo",
  "cidade": "Bauru",
  "feriados": [
    {
      "data": "01/01/2026",
      "tipo": "NACIONAL",
      "feriado": "Confraternização Universal"
    },
    {
      "data": "XX/XX/2026",
      "tipo": "MUNICIPAL",
      "feriado": "Nome do Feriado"
    }
  ]
}
```

### Resposta

Em caso de sucesso, a API não retorna conteúdo.

Em caso de erro, é esperado um objeto contendo a mensagem correspondente:

```json
{
  "mensagem": "MENSAGEM_DE_ERRO"
}
```


## 🐳 Executando com Docker

O projeto possui configuração para execução através do Docker Compose.

### Pré-requisitos

* Docker
* Docker Compose

### Inicialização

Clone o repositório:

```bash
git clone https://github.com/joviga-dev/RPA-Feriados-Selenium.git
```

Entre no diretório:

```bash
cd RPA-Feriados-Selenium
```

Execute os containers:

```bash
docker compose up --build
```

Após a inicialização, a aplicação estará disponível conforme as portas configuradas no `docker-compose.yml`.

### Parar a aplicação

```bash
docker compose down
```

## ⚙️ Configuração

As configurações sensíveis e específicas do ambiente devem ser fornecidas através de variáveis de ambiente.

Exemplo:

```env
DB_HOST=postgres
DB_PORT=5432
DB_NAME=feriados
DB_USERNAME=postgres
DB_PASSWORD=postgres

API_URL=https://example.com/api
API_TOKEN=your-token
```

> Não versionar tokens, senhas ou outras credenciais no repositório.

## 🧪 Execução local

Também é possível executar o projeto utilizando o Maven Wrapper.

No Windows:

```bash
./mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

Para gerar o build:

```bash
./mvnw clean package
```

## 📁 Estrutura do projeto

```text
RPA-Feriados-Selenium/
├── .mvn/
├── src/
│   └── ...
├── .env
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🎯 Objetivo

O projeto foi desenvolvido para demonstrar a construção de uma solução de **RPA integrada a uma aplicação backend**, combinando automação de navegador, persistência de dados e integração com APIs REST.

A solução busca manter cada etapa do processo desacoplada, facilitando manutenção, execução em ambientes containerizados e futuras evoluções da automação.

