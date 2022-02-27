package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test(groups = {"positiveTests", "smokeTests"}, priority = 1)
    public void register() {
        System.out.println("Starting register test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/parabank/register.htm";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        // Fill login fields, and click register
        driver.findElement(By.id("customer.firstName")).sendKeys("Dor");
        driver.findElement(By.id("customer.lastName")).sendKeys("Cohen");
        driver.findElement(By.id("customer.address.street")).sendKeys("Namir");
        driver.findElement(By.id("customer.address.city")).sendKeys("Tel Aviv");
        driver.findElement(By.id("customer.address.state")).sendKeys("Israel");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("1234567");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("0500000000");
        driver.findElement(By.id("customer.ssn")).sendKeys("575-82");
        driver.findElement(By.id("customer.username")).sendKeys("Extreme72");
        driver.findElement(By.id("customer.password")).sendKeys("qwe123");
        driver.findElement(By.id("repeatedPassword")).sendKeys("qwe123");
        driver.findElement(By.xpath("(//input[@value='Register'])")).click();
        String actVerificationText = "";
        String actExistErr = "";

        // Verification:

        // ########## IMPORTANT NOTE ##########:
        // The "ParaBank" site reset every other day. If the user already exist, the String of actExistErr will be:
        // "This username already exists."
        // Otherwise, it'll create the new user successfully. That's why we're using Try & Catch.
        try {
            actExistErr += driver.findElement(By.id("customer.username.errors")).getAttribute("innerText");
        } catch (NoSuchElementException e) {
            actVerificationText += driver.findElement(By.cssSelector("#rightPanel")).getAttribute("innerText");
        }
        String expVerificationText = """
                Welcome Extreme72
                                
                Your account was created successfully. You are now logged in.""";
        String expExistErrText = "This username already exists.";

        Assert.assertTrue(actVerificationText.equals(expVerificationText) ||
                actExistErr.equals(expExistErrText));

        // Close browser
        driver.quit();
    }

    @Test(groups = {"positiveTests", "smokeTests"}, priority = 2)
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
        try {
            String actVerificationText = driver.findElement(By.cssSelector("#leftPanel > p")).getAttribute("innerText");
            String expVerificationText = "Welcome Dor Cohen";
            Assert.assertEquals(actVerificationText, expVerificationText,
                    "Actual verification message is not as expected. \nActual: "
                            + actVerificationText + "\nExpected: "
                            + expVerificationText);
        } catch (NoSuchElementException e) {
            String actBrokenSiteErr;
            String expBrokenSiteErr;
            actBrokenSiteErr = driver.findElement(By.cssSelector("#rightPanel")).getAttribute("innerText");
            expBrokenSiteErr = """
                    Error!
                                        
                    The username and password could not be verified.""";
            if (actBrokenSiteErr.equals(expBrokenSiteErr)) {
                driver.quit();
                throw new SkipException("The site is broken. Please try again later.");
            } else {
                Assert.fail();
            }
        }
        // Close browser
        driver.quit();
    }

    @Test(groups = {"positiveTests", "smokeTests"})
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
        String actVerificationText = driver.findElement(By.cssSelector("#rightPanel > p:nth-child(2)"))
                .getAttribute("innerText");
        String expVerificationText = "Thank you Dor Cohen";
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
