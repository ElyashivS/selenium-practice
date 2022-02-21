package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test (groups = {"positiveTests", "smokeTests"})
    public void login() {
        System.out.println("Starting login test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        // Fill login fields, and click login
        WebElement usernameElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input"));
        usernameElement.sendKeys("Extreme72");
        WebElement passElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(4) > input"));
        passElement.sendKeys("qwe123");
        WebElement logInBtnElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input"));
        logInBtnElement.click();

        // Verification
        String actVerificationText = driver.findElement(By.cssSelector("#leftPanel > p")).getAttribute("innerText");
        String expVerificationText = "Welcome Dor Cohen";
        Assert.assertEquals(actVerificationText, expVerificationText,
                "Actual verification message is not as expected. \nActual: "
                        + actVerificationText + "\nExpected: "
                        + expVerificationText);

        // Close browser
        driver.quit();
    }

    @Test
    public void contact() {
        System.out.println("Starting contact test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        WebElement contactClick = driver.findElement(By.linkText("contact"));
        contactClick.click();

        // Fill all the required fields
        driver.findElement(By.id("name")).sendKeys("Dor Cohen");
        driver.findElement(By.id("email")).sendKeys("A7X@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("0500000000");
        driver.findElement(By.id("message")).sendKeys("Test");
        driver.findElement((By.xpath("(//input[@class='button'])[2]"))).click();

        // Verification
        String actVerificationText = driver.findElement(By.cssSelector("#rightPanel > p:nth-child(2)")).getAttribute("innerText");
        String expVerificationText = "Thank you Dor Cohen";
        System.out.println(actVerificationText);
        Assert.assertEquals(actVerificationText, expVerificationText,
                "Actual verification message is not as expected. \nActual: "
                        + actVerificationText + "\nExpected: "
                        + expVerificationText);

        // Close browser
        driver.quit();
    }

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
