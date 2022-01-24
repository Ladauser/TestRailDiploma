package webdriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.opera.OperaDriver;


@Log4j2
public class Driver {
    private Driver() {
    }

    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before(order = 1)
    public static void setup() {
        if (System.getProperty("browser") != null) {
            if (System.getProperty("browser").equals("chrome")) {
                WebDriverManager.chromedriver().setup();
                log.debug("Chrome browser is started!");
                driver = new ChromeDriver();
            } else if (System.getProperty("browser").equals("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else if (System.getProperty("browser").equals("opera")) {
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
            }
        } else {
            try {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } catch (Exception e) {
                log.fatal("FATAL ERROR: Driver is not started!");
            }
        }
        log.debug("Browser is started in fullscreen mode.");
        driver.manage().window().maximize();
    }

    @After
    public static void closeBrowser(Scenario scenario) {
        try {
            String screenshotName = scenario.getName().replaceAll("", "");
            if (scenario.isFailed()) {
                scenario.log("This is my failure message");
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", screenshotName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
