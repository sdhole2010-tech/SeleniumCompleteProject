package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenerClass implements ITestListener {

    private static final Logger log = LogManager.getLogger(ListenerClass.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
        extent = ExtentManager.createInstance(reportPath);
        log.info("📄 Extent Report created at: " + reportPath);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
        log.info("🚀 Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "✅ Test Passed: " + result.getName());
        log.info("✅ Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());
        log.error("❌ Test Failed: " + result.getName());

        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        if (driver != null) {
            String screenshotPath = captureScreenshot(driver, result.getName());
            testThread.get().addScreenCaptureFromPath(screenshotPath);
            log.info("📸 Screenshot attached: " + screenshotPath);
        } else {
            log.error("❗ WebDriver is null, cannot capture screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "⚠️ Test Skipped: " + result.getName());
        log.warn("⚠️ Test Skipped: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("📊 Extent Report generated successfully!");
    }

    public String captureScreenshot(WebDriver driver, String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = testName + "_" + timeStamp + ".png";
        String filePath = System.getProperty("user.dir") + "/screenshots/" + screenshotName;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(filePath);

        try {
            Files.createDirectories(destFile.getParentFile().toPath());
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
