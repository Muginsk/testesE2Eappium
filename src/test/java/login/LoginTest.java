package login;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.*;
import java.net.MalformedURLException;
import java.net.URL;
import io.appium.java_client.android.*;
import java.util.List;
import io.appium.java_client.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExtentReportUtil; // Importando o ExtentReportUtil
import com.aventstack.extentreports.ExtentTest;
import java.time.Duration;

public class LoginTest {
    private AndroidDriver driver;
    private static ExtentTest test; // Variável para cada teste

    @BeforeAll
    public static void setupClass() {
        System.out.println("Iniciando os testes...");
        ExtentReportUtil.setupReport(); // Inicia o relatório
    }

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("Pixel 7 Pro")
                .setAppPackage("com.swaglabsmobileapp")
                .setAppActivity("com.swaglabsmobileapp.MainActivity")
                .setUdid("emulator-5554")
                .setAutomationName("UiAutomator2")
                .setEnsureWebviewsHavePages(true)
                .setNativeWebScreenshot(true);

        URL appiumServerUrl = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(appiumServerUrl, options);
    }

    @Test
    public void testLoginComSucesso() {
        test = ExtentReportUtil.createTest("Teste de Login com Sucesso");
        test.info("Iniciando teste de login...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("problem_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        test.pass("Login realizado com sucesso.");
    }

    @Test
    public void testCompraCompleta() throws InterruptedException {
        test = ExtentReportUtil.createTest("Teste de Compra Completa");
        test.info("Iniciando teste de compra...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Login
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-Username")));
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        test.pass("Login realizado com sucesso.");

        // Valida Tela de Produtos
        Assertions.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']")).isDisplayed());
        test.pass("Tela de produtos carregada.");

        // Adicionando item ao carrinho
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']")).click();
        driver.findElement(AppiumBy.accessibilityId("test-ADD TO CART")).click();
        driver.findElement(AppiumBy.accessibilityId("test-BACK TO PRODUCTS")).click();
        test.pass("Produto adicionado ao carrinho.");

        // Acessando carrinho
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']")));
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']")).click();
        test.pass("Carrinho acessado.");

        // Verifica produto no carrinho
        List<WebElement> produtosNoCarrinho = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']"));
        Assertions.assertEquals(produtosNoCarrinho.size(), 1, "Quantidade errada de produtos no carrinho!");
        test.pass("Produto encontrado no carrinho.");

        // Checkout
        driver.findElement(AppiumBy.accessibilityId("test-CHECKOUT")).click();
        test.pass("Iniciando checkout.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-First Name")));
        driver.findElement(AppiumBy.accessibilityId("test-First Name")).sendKeys("Felipe");
        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys("Teste");
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys("06700287");
        driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).click();
        test.pass("Dados de checkout preenchidos.");

        // Finalizar compra
        driver.findElement(AppiumBy.accessibilityId("test-FINISH")).click();
        test.pass("Compra finalizada.");

        // Validar mensagem de sucesso
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']")));
        Assertions.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']")).isDisplayed());
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
        ExtentReportUtil.flushReport(); // Finaliza o relatório
        System.out.println("Finalizando os testes...");
    }
}