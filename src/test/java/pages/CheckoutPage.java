package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private AndroidDriver driver;
    private static final int MAX_RETRIES = 3;

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void clicarNoCarrinho() {
        retry(() -> {
            WebElement botaoCarrinho = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("test-Cart")));
            botaoCarrinho.click();
        }, "Erro ao clicar no botão do carrinho");
    }

    public void iniciarCheckout() {
        clicarNoCarrinho();
        retry(() -> {
            WebElement botaoCheckout = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("test-CHECKOUT")));
            botaoCheckout.click();
        }, "Erro ao iniciar o checkout");
    }

    public void preencherDadosCheckout(String primeiroNome, String sobrenome, String cep) {
        retry(() -> {
            WebElement campoPrimeiroNome = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-First Name")));
            campoPrimeiroNome.clear();
            campoPrimeiroNome.sendKeys(primeiroNome);

            WebElement campoSobrenome = driver.findElement(AppiumBy.accessibilityId("test-Last Name"));
            campoSobrenome.clear();
            campoSobrenome.sendKeys(sobrenome);

            WebElement campoCep = driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code"));
            campoCep.clear();
            campoCep.sendKeys(cep);
        }, "Erro ao preencher os dados do checkout");
    }

    public void clicarNoCheckout() {
        retry(() -> {
            WebElement botaoCheckout = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text='CONTINUE']")));
            botaoCheckout.click();
        }, "Erro ao clicar no botão de Checkout");
    }

    public void finalizarCompra() {
        retry(() -> {
            WebElement botaoFinalizar = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text='FINISH']")));
            botaoFinalizar.click();
        }, "Erro ao finalizar a compra");
    }

    public boolean validarCompraConcluida() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-CHECKOUT: COMPLETE!")))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void retry(Runnable action, String errorMessage) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                action.run();
                return;
            } catch (TimeoutException | NoSuchElementException e) {
                System.out.println(errorMessage + " - Tentativa " + (attempt + 1));
            }
            attempt++;
        }
        throw new RuntimeException(errorMessage + " após " + MAX_RETRIES + " tentativas.");
    }
}
