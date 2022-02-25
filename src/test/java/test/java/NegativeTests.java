package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {

    @Test(groups = {"negativeTests", "smokeTests"})
    public void emptyUsername() {
        System.out.println("Starting empty Username test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Contact Us page opened.");

        // Fill Username without password and click login
        WebElement usernameElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input"));
        usernameElement.sendKeys("Extreme72");
        WebElement logInBtnElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input"));
        logInBtnElement.click();

        // Verifications
        String actVerificationText = driver.findElement(By.cssSelector("#rightPanel")).getAttribute("innerText");
        String expVerificationText = """
                Error!

                An internal error has occurred and has been logged.""";
        String expVerificationText2 = """
                Error!
                
                Please enter a username and password.""";

        Assert.assertTrue(actVerificationText.equals(expVerificationText) ||
                        actVerificationText.equals(expVerificationText2));
        driver.quit();
    }

    @Test(groups = {"negativeTests", "smokeTests"})
    @Parameters({"password"})
    public void incorrectPassword(String password) {
        System.out.println("Starting Incorrect Password test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        // Fill Username and incorrect password, and click login
        WebElement usernameElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input"));
        usernameElement.sendKeys("Extreme72");
        WebElement passElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(4) > input"));
        passElement.sendKeys(password);
        WebElement logInBtnElement = driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input"));
        logInBtnElement.click();

        // Verification
        String actVerificationText = driver.findElement(By.cssSelector("#rightPanel")).getAttribute("innerText");
        String expVerificationText = """
                Error!

                An internal error has occurred and has been logged.""";
        String expVerificationText2 = """
                Error!
                                
                The username and password could not be verified.""";

        Assert.assertTrue(actVerificationText.equals(expVerificationText) ||
                        actVerificationText.equals(expVerificationText2));

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
