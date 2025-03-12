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
        ExtentReportUtil.setupReport();
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Pixel 7 Pro")
                .setAppPackage("com.swaglabsmobileapp")
                .setAppActivity("com.swaglabsmobileapp.MainActivity")
                .setUdid("emulator-5554")
                .setAutomationName("UiAutomator2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testLoginComSucesso() {
        test = ExtentReportUtil.createTest("Teste de Login com Sucesso");
        loginPage.preencherCredenciais("problem_user", "secret_sauce");
        loginPage.clicarLogin();
        Assertions.assertTrue(loginPage.validarTelaProdutos());
        test.pass("Login realizado com sucesso.");
    }

    @Test
    public void testCompraCompleta() {
        test = ExtentReportUtil.createTest("Teste de Compra Completa");

        loginPage.preencherCredenciais("standard_user", "secret_sauce");
        loginPage.clicarLogin();
        Assertions.assertTrue(loginPage.validarTelaProdutos());
        test.pass("Login realizado com sucesso.");

        checkoutPage.preencherDadosCheckout("Felipe", "Teste", "06700287");
        checkoutPage.finalizarCompra();
        Assertions.assertTrue(checkoutPage.validarCompraConcluida());
        test.pass("Compra concluída com sucesso!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void teardownClass() {
        ExtentReportUtil.flushReport();
    }
}
