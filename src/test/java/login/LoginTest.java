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

import javax.lang.model.element.Element;
import java.time.Duration;

public class LoginTest {
    private AndroidDriver driver;

    @BeforeAll
    public static void setupClass() {
        System.out.println("Iniciando os testes...");
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
        System.out.println("Iniciando teste de login...");

        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("problem_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();

        System.out.println("Login realizado com sucesso.");
    }

    @Test
    public void testCompraCompleta() throws InterruptedException {
        System.out.println("Iniciando teste de compra completa...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Acessar a tela de login e realizar login
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        System.out.println("Login realizado com sucesso.");

        // Validar que a tela de produtos foi carregada
        Assertions.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']")).isDisplayed());


        // Adicionando item ao carrinho
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']")).click();
        driver.findElement(AppiumBy.accessibilityId("test-ADD TO CART")).click();
        driver.findElement(AppiumBy.accessibilityId("test-BACK TO PRODUCTS")).click();
        System.out.println("Produto adicionado ao carrinho.");



        // Acessando o carrinho
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']")));
        driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']")).click();
        System.out.println("Carrinho acessado.");

        // Verificando se o item foi adicionado

        List<WebElement> produtosNoCarrinho = driver.findElements(AppiumBy.xpath("//android.widget.TextView[@text='Sauce Labs Backpack']"));
        Assertions.assertEquals(produtosNoCarrinho.size(), 1, "Quantidade errada de produtos no carrinho!");

        // Prosseguindo para o checkout
        driver.findElement(AppiumBy.accessibilityId("test-CHECKOUT")).click();
        System.out.println("Iniciando checkout.");

        // Preenchendo os dados de checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-First Name")));
        driver.findElement(AppiumBy.accessibilityId("test-First Name")).sendKeys("Felipe");
        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys("Teste");
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys("06700287");
        driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).click();
        System.out.println("Dados de checkout preenchidos.");


        // Validar a tela de revisão
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: OVERVIEW']")));
        Assertions.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: OVERVIEW']")).isDisplayed());


        // Finalizar a compra
        driver.findElement(AppiumBy.accessibilityId("test-FINISH")).click();
        System.out.println("Compra finalizada.");

        // Validar mensagem de sucesso

        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']")));
        Assertions.assertTrue(driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']")).isDisplayed());

        System.out.println("Teste finalizado com sucesso!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void teardownClass() {
        System.out.println("Finalizando os testes...");
    }
}
