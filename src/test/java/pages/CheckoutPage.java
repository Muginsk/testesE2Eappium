package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void preencherDadosCheckout(String firstName, String lastName, String zipCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("test-First Name")));
        driver.findElement(AppiumBy.accessibilityId("test-First Name")).sendKeys(firstName);
        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys(lastName);
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys(zipCode);
        driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).click();
    }

    public void finalizarCompra() {
        driver.findElement(AppiumBy.accessibilityId("test-FINISH")).click();
    }

    public boolean validarCompraConcluida() {
        return driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']")).isDisplayed();
    }
}
