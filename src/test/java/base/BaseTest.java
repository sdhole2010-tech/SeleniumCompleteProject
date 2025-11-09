package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;
    protected Properties prop;
    protected Logger log = LogManager.getLogger(this.getClass());

    @BeforeClass
    public void setUp(ITestContext context) throws IOException {

        // Load config.properties
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);

        // Launch ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Get URL from properties file
        String URL = prop.getProperty("URL");
        driver.get(URL);

        // Store WebDriver reference in ITestContext (used by ListenerClass)
        context.setAttribute("WebDriver", driver);

        log.info("Browser launched and navigated to: " + URL);
    }

    @AfterClass
    public void cleanUp() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully.");
        }
    }
}
