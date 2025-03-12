package login;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import java.net.MalformedURLException;
import java.net.URL;
import pages.LoginPage;
import pages.CheckoutPage;
import utils.ExtentReportUtil;
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

public class LoginTest {
    private AndroidDriver driver;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;
    private static ExtentTest test;

    @BeforeAll
    public static void setupClass() {
        System.out.println("Iniciando relatório de testes...");
        ExtentReportUtil.setupReport();
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        System.out.println("Configurando driver para o teste...");
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Pixel 7 Pro")
                .setAppPackage("com.swaglabsmobileapp")
                .setAppActivity("com.swaglabsmobileapp.MainActivity")
                .setUdid("emulator-5554")
                .setAutomationName("UiAutomator2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        System.out.println("Driver configurado com sucesso!");

        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testLoginComSucesso() {
        System.out.println("Iniciando Teste de Login...");
        test = ExtentReportUtil.createTest("Teste de Login com Sucesso");

        System.out.println("Preenchendo credenciais...");
        loginPage.preencherCredenciais("standard_user", "secret_sauce");

        System.out.println("Clicando em login...");
        loginPage.clicarLogin();

        System.out.println("Validando tela de produtos...");
        Assertions.assertTrue(loginPage.validarTelaProdutos(), "A tela de produtos não foi carregada corretamente!");

        test.pass("Login realizado com sucesso.");
        System.out.println("Teste de Login finalizado com sucesso!");
    }

    @Test
    public void testCompraCompleta() {
        System.out.println("Iniciando Teste de Compra Completa...");
        test = ExtentReportUtil.createTest("Teste de Compra Completa");

        System.out.println("Preenchendo credenciais...");
        loginPage.preencherCredenciais("standard_user", "secret_sauce");

        System.out.println("Clicando em login...");
        loginPage.clicarLogin();

        System.out.println("Validando tela de produtos...");
        Assertions.assertTrue(loginPage.validarTelaProdutos(), "A tela de produtos não foi carregada corretamente!");

        test.pass("Login realizado com sucesso.");
        System.out.println("Login realizado com sucesso!");

        System.out.println("Clicando no carrinho...");
        checkoutPage.clicarNoCarrinho(); // metodo para garantir que o carrinho seja acessado

        System.out.println("Iniciando checkout...");
        checkoutPage.iniciarCheckout(); // metodo para iniciar o checkout corretamente

        System.out.println("Preenchendo dados do checkout...");
        checkoutPage.preencherDadosCheckout("Felipe", "Teste", "06700287");

        System.out.println("Clicando no checkout...");
        checkoutPage.clicarNoCheckout(); // metodo para garantir que o checkout seja clicado

        System.out.println("Finalizando compra...");
        checkoutPage.finalizarCompra();

        System.out.println("Validando se a compra foi concluída...");
        Assertions.assertTrue(checkoutPage.validarCompraConcluida(), "A compra não foi concluída com sucesso!");

        test.pass("Compra concluída com sucesso!");
        System.out.println("Teste de Compra Completa finalizado com sucesso!");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Encerrando driver...");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void teardownClass() {
        System.out.println("Finalizando relatório de testes...");
        ExtentReportUtil.flushReport();
    }
}
