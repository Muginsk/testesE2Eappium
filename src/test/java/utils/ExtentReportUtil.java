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
        try {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        } catch (Exception e) {
            System.err.println("Erro ao configurar ExtentReports: " + e.getMessage());
            e.printStackTrace();
        }
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

        int attempts = 0;
        boolean success = false;
        String directory = "C:/PROJETOSQA/testesE2Eappium/screenshots";  // Caminho fixo
        String filePath = directory + "/" + testName + ".png";

        while (attempts < 3 && !success) {
            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.createDirectories(Paths.get(directory)); // Cria a pasta caso não exista
                Files.copy(srcFile.toPath(), Paths.get(filePath));
                test.addScreenCaptureFromPath(filePath.replace("\\", "/")); // Garante compatibilidade no relatório
                test.log(Status.INFO, "Screenshot capturada: " + testName);
                success = true;
            } catch (IOException e) {
                attempts++;
                if (attempts == 3) {
                    test.log(Status.WARNING, "Falha ao salvar screenshot após múltiplas tentativas: " + e.getMessage());
                }
            } catch (Exception e) {
                test.log(Status.FAIL, "Erro inesperado ao capturar screenshot: " + e.getMessage());
                e.printStackTrace();
                break;
            }
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
