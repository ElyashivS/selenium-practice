package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NegativeTests {

    @Test(groups = {"negativeTests", "smokeTests"})
    public void emptyUsername() {
        System.out.println("Starting empty Username test");

        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Contact Us page opened.");

        // Fill Username without password and click login
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input")).sendKeys("Extreme72");
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input")).click();

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

        // Closer browser
        driver.quit();
    }

    @Test(groups = {"negativeTests", "smokeTests"})
    @Parameters({"password"})
    public void incorrectPassword(String password) {
        System.out.println("Starting Incorrect Password test");

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
                        actVerificationText.equals(expVerificationText2),
                "Actual verification message is not as expected. \nActual: "
                + actVerificationText + "\nExpected: "
                + expVerificationText + "Or: " +expVerificationText2);

        // Close browser
        driver.quit();
    }

    @Test
    public void responseTest() {
        try {
            URL url = new URL("https://parabank.parasoft.com/parabank/services/bank/withdraw?accountId=13788&amount=10000000");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Assert.assertEquals(con.getResponseCode(), 405);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
