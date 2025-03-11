package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportUtil {
    private static ExtentReports extent;

    // Inicializa o relatório
    public static void setupReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    // Cria um novo teste no relatório
    public static ExtentTest createTest(String testName) {
        return extent.createTest(testName);
    }

    // Finaliza o relatório
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
