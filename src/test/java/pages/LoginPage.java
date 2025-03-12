package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private AndroidDriver driver;

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    private static final int MAX_RETRIES = 3;

    public void preencherCredenciais(String usuario, String senha) {
        retry(() -> {
            WebElement campoUsuario = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-Username")));
            campoUsuario.clear();
            campoUsuario.sendKeys(usuario);

            WebElement campoSenha = driver.findElement(AppiumBy.accessibilityId("test-Password"));
            campoSenha.clear();
            campoSenha.sendKeys(senha);
        }, "Falha ao preencher credenciais.");
    }

    public void clicarLogin() {
        retry(() -> {
            WebElement botaoLogin = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("test-LOGIN")));
            botaoLogin.click();
        }, "Falha ao clicar no botão de login.");
    }

    public boolean validarTelaProdutos() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='PRODUCTS']"))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void retry(Runnable action, String errorMessage) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                action.run();
                return; // Sai do loop se for bem-sucedido
            } catch (TimeoutException | NoSuchElementException e) {
                System.out.println(errorMessage + " Tentativa " + (attempt + 1) + " falhou.");
            }
            attempt++;
        }
        throw new RuntimeException(errorMessage + " Após " + MAX_RETRIES + " tentativas.");
    }
}
