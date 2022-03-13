package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class DockerTests {
    WebDriver driver;
    static final String APP_URL = "https://parabank.parasoft.com/";
    static final String HOTS_URL = "http://localhost:4444";

    @Test
    public void contactViaChrome() {
        try {
            ChromeOptions opt = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(HOTS_URL), opt);
            driver.navigate().to(APP_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Starting contact test through Docker via Chrome");
        test();
        System.out.println("Chrome test finished");
    }

    @Test
    public void contactViaFireFox() {
        try {
            FirefoxOptions opt = new FirefoxOptions();
            driver = new RemoteWebDriver(new URL(HOTS_URL), opt);
            driver.navigate().to(APP_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Starting contact test through Docker via FireFox");
        test();
        System.out.println("FireFox test finished");
    }

    @Test
    public void contactViaEdge() {
        try {
            EdgeOptions opt = new EdgeOptions();
            driver = new RemoteWebDriver(new URL(HOTS_URL), opt);
            driver.navigate().to(APP_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("Starting contact test through Docker via Edge");
        test();
        System.out.println("Edge test finished");
    }

    private void test() {
        WebElement contactClick = driver.findElement(By.linkText("contact"));

        System.out.println("ParaBank main page opened.");
        contactClick.click();

        System.out.println("Contact Us clicked");
        // Fill all the required fields
        driver.findElement(By.id("name")).sendKeys("Dor Cohen");
        driver.findElement(By.id("email")).sendKeys("A7X@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("0500000000");
        driver.findElement(By.id("message")).sendKeys("Test");
        driver.findElement((By.xpath("(//input[@class='button'])[2]"))).click();

        // Verification
        System.out.println("Starting Verification");
        String actVerificationText = driver.findElement(By.cssSelector("#rightPanel > p:nth-child(2)")).getAttribute("innerText");
        String expVerificationText = "Thank you Dor Cohen";
        Assert.assertEquals(actVerificationText, expVerificationText, "Actual verification message is not as expected. \nActual: " + actVerificationText + "\nExpected: " + expVerificationText);

        // Close browser
        driver.quit();
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
