package test.java;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DockerTests {
    WebDriver driver;
    static final String APP_URL = "https://www.google.com/";
    static final String HOTS_URL = "http://localhost:4444";

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        ChromeOptions opt = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(HOTS_URL), opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to(APP_URL);
    }

    @Test
    public void assertTitle() {
        String expTitle = "Google";
        System.out.println("Actual title result " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains(expTitle));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
