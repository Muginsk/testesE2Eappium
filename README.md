# Testes Automatizados com Appium

Este projeto contém testes automatizados utilizando **Appium** para validar o fluxo de login e compra no aplicativo *Swag Labs*.

## 📋 Pré-requisitos
Antes de executar os testes, certifique-se de ter os seguintes requisitos instalados:
- **Java 11+**
- **Maven**
- **Appium Server**
- **Node.js** e **npm** (para instalar o Appium)
- **Android SDK** e **Emulador Android**

## 🔧 Configuração
1. Clone este repositório:
   ```sh
   git clone https://github.com/seuusuario/appium-tests.git
   cd appium-tests
   ```
2. Instale as dependências:
   ```sh
   mvn clean install
   ```
3. Inicie o servidor Appium:
   ```sh
   appium
   ```

## 🚀 Executando os Testes
Para executar os testes automatizados, use o comando:
```sh
mvn test
```

## 📄 Relatório de Testes
Os relatórios de execução são gerados automaticamente na pasta:
```
appium-tests/reports/ExtentReport.html
```
Para visualizar o relatório, abra o arquivo `ExtentReport.html` em qualquer navegador.

## 📌 Testes Implementados
- **LoginTest**: Testa o login com um usuário válido.
- **CompraTest**: Testa o fluxo completo de compra de um produto.

## 🛠 Tecnologias Utilizadas
- **Java**
- **Appium**
- **JUnit 5**
- **Extent Reports** (para geração de relatórios)

## 📢 Contato
Caso tenha dúvidas ou sugestões, entre em contato pelo [seu e-mail ou GitHub].

