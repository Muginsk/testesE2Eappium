package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentReportUtil {
    private static ExtentReports extent;

    public static void setupReport() {
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }


    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        test.log(Status.INFO, "Iniciando o teste: " + testName);
        return test;
    }

    public static void logInfo(ExtentTest test, String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void logWarning(ExtentTest test, String message) {
        if (test != null) {
            test.log(Status.WARNING, message);
        }
    }

    public static void logError(ExtentTest test, String message, Throwable throwable) {
        if (test != null) {
            test.log(Status.FAIL, message);
            test.fail(throwable);
        }
    }

    public static void captureScreenshot(AndroidDriver driver, ExtentTest test, String testName) {
        if (driver == null || test == null) return;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        String filePath = screenshotDir + testName + ".png"; // Caminho absoluto

        try {
            Files.createDirectories(Paths.get(screenshotDir));
            Files.copy(srcFile.toPath(), Paths.get(filePath));

            // Adiciona o caminho absoluto ao relatório
            test.addScreenCaptureFromPath(filePath);
            test.log(Status.INFO, "Screenshot capturada: " + testName);
        } catch (IOException e) {
            test.log(Status.WARNING, "Falha ao salvar screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void captureScreenshotOnFailure(AndroidDriver driver, ExtentTest test, String testName, Throwable throwable) {
        logError(test, "Teste falhou: " + testName, throwable);
        captureScreenshot(driver, test, testName);
    }

    public static void endTest(ExtentTest test, long startTime) {
        if (test != null) {
            long duration = (System.currentTimeMillis() - startTime) / 1000; // Tempo em segundos
            test.log(Status.INFO, "Teste finalizado em " + duration + " segundos.");
        }
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
