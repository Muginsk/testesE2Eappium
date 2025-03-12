# Testes E2E com Appium

## 📌 Visão Geral
Este repositório contém testes de automação E2E utilizando Appium para aplicações móveis. Aqui você encontrará instruções detalhadas para configuração do ambiente, execução dos testes e solução de problemas comuns.

---

## 🛠 Configuração do Ambiente

### 🔹 Pré-requisitos
Antes de iniciar, certifique-se de ter os seguintes componentes instalados:
- **Java JDK 11+**
- **Android SDK** (incluso no Android Studio)
- **Node.js (versão 14 ou superior)**
- **Appium Server**
- **Appium Inspector** (Opcional, mas útil para inspecionar elementos da UI)
- **IntelliJ IDEA** (ou outro IDE de sua escolha)
- **Drivers necessários** para os dispositivos/emuladores que serão testados

### 🔹 Instalação do Appium

Execute os seguintes comandos para instalar o Appium globalmente:
```sh
npm install -g appium
```
Para verificar se a instalação foi concluída com sucesso, execute:
```sh
appium -v
```

### 🔹 Configuração do Android SDK
Adicione as seguintes variáveis de ambiente no seu sistema:
```sh
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH
```
Verifique a instalação executando:
```sh
adb devices
```
Isso deve listar os dispositivos conectados.

---

## 🚀 Execução dos Testes

### 🔹 Iniciando o Appium Server
Antes de executar os testes, o servidor Appium deve estar rodando. Utilize o seguinte comando:
```sh
appium
```
Se quiser rodar o Appium em background:
```sh
appium &
```

### 🔹 Executando os Testes
Para rodar todos os testes automatizados, utilize:
```sh
mvn test
```
Para executar um teste específico:
```sh
mvn -Dtest=NomeDoTeste test
```

### 🔹 Configuração de Emulador
Se precisar iniciar um emulador manualmente, utilize:
```sh
emulator -avd NomeDoEmulador
```
Para listar os emuladores disponíveis:
```sh
emulator -list-avds
```

---

## 🛑 Solução de Problemas Comuns

### ❌ Erro: "No devices/emulators found"
**Solução:**
1. Verifique se o emulador está rodando (`adb devices`)
2. Se necessário, reinicie o servidor ADB:
   ```sh
   adb kill-server
   adb start-server
   ```

### ❌ Erro: "Appium could not start"
**Solução:**
- Verifique se outra instância do Appium está rodando e finalize-a.
- Rode o Appium com mais detalhes:
  ```sh
  appium --log-level debug
  ```

### ❌ Erro: "SessionNotCreatedException"
**Solução:**
- Certifique-se de que o APK ou pacote de testes está correto.
- Verifique se o driver do Appium está atualizado:
  ```sh
  npm update -g appium
  ```

---

## 📂 Estrutura do Projeto
```
📂 testesE2Eappium
 ├── 📂 src
 │   ├── 📂 main
 │   │   ├── 📂 java
 │   │   └── 📂 resources
 │   ├── 📂 test
 │   │   ├── 📂 java
 │   │   └── 📂 resources
 ├── 📄 pom.xml
 ├── 📄 README.md
 ├── 📄 appium-config.json
```

---

## 📊 Relatórios e Logs
Os screenshots dos testes são salvos automaticamente em:
```sh
C:\PROJETOSQA\testesE2Eappium\test-output
```
Para visualizar logs detalhados, consulte o diretório de saída do Maven.

---

## 📩 Contato
Se encontrar problemas ou tiver sugestões, abra uma issue! 🚀
