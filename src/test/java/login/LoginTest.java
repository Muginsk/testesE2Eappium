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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import javax.security.auth.login.LoginException;

@TestMethodOrder(OrderAnnotation.class)
public class LoginTest {
    private AndroidDriver driver;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;
    private ExtentTest test;
    private long startTime;

    @BeforeAll
    public static void setupClass() {
        System.out.println("Iniciando relatório de testes...");
        ExtentReportUtil.setupReport();
    }

    @BeforeEach
    public void setUp() {
        try {
            System.out.println("Configurando driver para o teste...");
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setDeviceName("Pixel_7_Pro_API_34")
                    .setAppPackage("com.swaglabsmobileapp")
                    .setAppActivity("com.swaglabsmobileapp.MainActivity")
                    .setUdid("emulator-5554")
                    .setAutomationName("UiAutomator2");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4725"), options);
            System.out.println("Driver configurado com sucesso!");

            loginPage = new LoginPage(driver);
            checkoutPage = new CheckoutPage(driver);
        } catch (Exception e) {
            Assertions.fail("Erro ao configurar o driver: " + e.getMessage());
        }
    }


    @Test
    @Order(1)
    public void testLoginComSucesso() {
        test = ExtentReportUtil.createTest("Teste de Login com Sucesso");
        startTime = System.currentTimeMillis();

        try {
            loginPage.preencherCredenciais("standard_user", "secret_sauce");
            ExtentReportUtil.logInfo(test, "Preencheu credenciais");

            loginPage.clicarLogin();
            ExtentReportUtil.logInfo(test, "Clicou no botão de login");

            if (!loginPage.validarTelaProdutos()) {
                throw new LoginException("A tela de produtos não foi carregada corretamente!");
            }
            ExtentReportUtil.logInfo(test, "Validação da tela de produtos bem-sucedida");

            ExtentReportUtil.captureScreenshot(driver, test, "testLoginComSucesso");
        } catch (LoginException e) {
            ExtentReportUtil.captureScreenshotOnFailure(driver, test, "testLoginComSucesso", e);
            Assertions.fail("Erro no login: " + e.getMessage());
        } catch (Exception e) {
            ExtentReportUtil.captureScreenshotOnFailure(driver, test, "testLoginComSucesso", e);
            Assertions.fail("Erro inesperado: " + e.getMessage());
        } finally {
            ExtentReportUtil.endTest(test, startTime);
        }
    }

    @Test
    @Order(2)
    public void testCompraCompleta() {
        test = ExtentReportUtil.createTest("Teste de Compra Completa");
        startTime = System.currentTimeMillis();

        try {
            loginPage.preencherCredenciais("standard_user", "secret_sauce");
            ExtentReportUtil.logInfo(test, "Preencheu credenciais");

            loginPage.clicarLogin();
            ExtentReportUtil.logInfo(test, "Clicou no botão de login");

            Assertions.assertTrue(loginPage.validarTelaProdutos(), "A tela de produtos não foi carregada corretamente!");
            ExtentReportUtil.logInfo(test, "Validação da tela de produtos bem-sucedida");

            checkoutPage.clicarNoCarrinho();
            ExtentReportUtil.logInfo(test, "Acessou o carrinho");

            checkoutPage.iniciarCheckout();
            ExtentReportUtil.logInfo(test, "Iniciou checkout");

            checkoutPage.preencherDadosCheckout("Felipe", "Teste", "06700287");
            ExtentReportUtil.logInfo(test, "Preencheu os dados do checkout");

            checkoutPage.clicarNoCheckout();
            ExtentReportUtil.logInfo(test, "Clicou no checkout");

            checkoutPage.finalizarCompra();
            ExtentReportUtil.logInfo(test, "Finalizou a compra");

            Assertions.assertTrue(checkoutPage.validarCompraConcluida(), "A compra não foi concluída com sucesso!");
            ExtentReportUtil.logInfo(test, "Compra validada com sucesso");

            ExtentReportUtil.captureScreenshot(driver, test, "testCompraCompleta_Sucesso");
        } catch (Exception e) {
            ExtentReportUtil.captureScreenshotOnFailure(driver, test, "testCompraCompleta_Fail", e);
            Assertions.fail(e.getMessage());
        } finally {
            ExtentReportUtil.endTest(test, startTime);
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Encerrando driver...");
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.err.println("Erro ao encerrar o driver: " + e.getMessage());
        }
    }


    @AfterAll
    public static void teardownClass() {
        System.out.println("Finalizando relatório de testes...");
        ExtentReportUtil.flushReport();
    }
}
