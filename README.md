# Testes Automatizados E2E com Appium

## 📌 Descrição

Este projeto contém testes automatizados **End-to-End (E2E)** para aplicativos móveis utilizando **Appium**. Os testes garantem a funcionalidade da aplicação em dispositivos reais ou emuladores.

## 🚀 Tecnologias Utilizadas

- **Java** - Linguagem utilizada para os testes  
- **Appium** - Framework para automação de aplicativos móveis  
- **JUnit** - Framework de testes unitários  
- **Maven** - Gerenciador de dependências  
- **ExtentReports** - Geração de relatórios detalhados  
- **GitHub Actions** - Para execução dos testes em CI/CD  

## 📂 Estrutura do Projeto
```
/testesE2Eappium │── src/test/java/ │ ├── tests/ # Casos de Teste │ ├── utils/ # Classes utilitárias │── pom.xml # Gerenciador de dependências Maven │── README.md # Documentação do projeto │── reports/ # Relatórios gerados pelo ExtentReports
```
## 🛠️ Pré-requisitos

Antes de rodar os testes, certifique-se de ter instalado:

- **Java** (versão 11 ou superior)  
- **Maven**  
- **Appium Server** instalado e rodando  
- **Android SDK** configurado corretamente  

Para instalar as dependências, execute:

```sh
mvn clean install
```
## ▶️ Como Executar os Testes
# 🔹 Configurando o Appium Server
Certifique-se de que o Appium Server está rodando. Você pode iniciar pelo terminal com o comando:
```sh

appium
```
Verifique se o emulador/dispositivo real está conectado:
```sh

adb devices
```
#🔹 Executando os Testes
Para rodar todos os testes via Maven:
```sh

mvn test
```
Executar testes específicos:
```sh
mvn -Dtest=NomeDaClasseDeTeste test
```
## 💊 Relatório de Testes
Os relatórios de execução dos testes são gerados automaticamente na pasta reports/.

## 📣 Importância do Relatório
Os relatórios fornecem uma visão detalhada dos testes executados, incluindo:

Testes aprovados e falhos
Tempo de execução de cada teste
Screenshots capturadas em falhas
Isso facilita a identificação de problemas e melhora a qualidade do software.

## 🔗 Como Acessar o Relatório ExtentReports
Após a execução dos testes, o relatório pode ser acessado abrindo o arquivo gerado dentro da pasta reports/.

## 🛠️ Configuração no GitHub Actions
O projeto possui um workflow configurado para executar os testes automaticamente no GitHub Actions. O workflow está localizado em:
```
bash

.github/workflows/github_actions_e2e.yml
```
## 📝 Licença
Este projeto está sob a licença MIT. Sinta-se livre para utilizá-lo e contribuir! 

