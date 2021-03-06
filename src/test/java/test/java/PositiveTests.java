package test.java;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PositiveTests {

    Object configJsonParser;
    Object paramsJsonParser;

    JSONObject configJsonObject;
    JSONObject paramsJsonObject;

    @BeforeSuite
    public void before_suite() {
        System.out.println("Starting set up");
        String driverLocation = "";
        // Read from JSON
        try {
            configJsonParser = new JSONParser().parse(new FileReader("src/config/config.json"));
            configJsonObject = (JSONObject) configJsonParser;
            driverLocation += (String) configJsonObject.get("chromeDriverLocation");

            paramsJsonParser = new JSONParser().parse(new FileReader("src/config/params.json"));
            paramsJsonObject = (JSONObject) paramsJsonParser;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.chrome.driver", driverLocation);
    }

    @Test(groups = {"positiveTests", "smokeTests"}, priority = -1)
    public void register() {
        System.out.println("Starting register test");

        WebDriver driver = new ChromeDriver();
        // Open test page
        String url = "https://parabank.parasoft.com/parabank/register.htm";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        // Fill login fields using json, and click register
        driver.findElement(By.id("customer.firstName")).sendKeys((CharSequence) paramsJsonObject.get("firstName"));
        driver.findElement(By.id("customer.lastName")).sendKeys((CharSequence) paramsJsonObject.get("lastName"));
        driver.findElement(By.id("customer.address.street")).sendKeys((CharSequence) paramsJsonObject.get("address.street"));
        driver.findElement(By.id("customer.address.city")).sendKeys((CharSequence) paramsJsonObject.get("address.city"));
        driver.findElement(By.id("customer.address.state")).sendKeys((CharSequence) paramsJsonObject.get("address.state"));
        driver.findElement(By.id("customer.address.zipCode")).sendKeys((CharSequence) paramsJsonObject.get("address.zipCode"));
        driver.findElement(By.id("customer.phoneNumber")).sendKeys((CharSequence) paramsJsonObject.get("phoneNumber"));
        driver.findElement(By.id("customer.ssn")).sendKeys((CharSequence) paramsJsonObject.get("ssn"));
        driver.findElement(By.id("customer.username")).sendKeys((CharSequence) paramsJsonObject.get("username"));
        driver.findElement(By.id("customer.password")).sendKeys((CharSequence) paramsJsonObject.get("password"));
        driver.findElement(By.id("repeatedPassword")).sendKeys((CharSequence) paramsJsonObject.get("repeatedPassword"));
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

        Assert.assertTrue(actVerificationText.equals(expVerificationText) || actExistErr.equals(expExistErrText));

        // Close browser
        driver.quit();
    }

    @Test(groups = {"positiveTests", "smokeTests"})
    public void login() {
        System.out.println("Starting login test");

        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank main page opened.");

        // Fill login fields, and click login
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input")).sendKeys((CharSequence) paramsJsonObject.get("username"));
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(4) > input")).sendKeys((CharSequence) paramsJsonObject.get("password"));
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input")).click();

        // Verification
        try {
            String actVerificationText = driver.findElement(By.cssSelector("#leftPanel > p")).getAttribute("innerText");
            String expVerificationText = "Welcome Dor Cohen";
            Assert.assertEquals(actVerificationText, expVerificationText, "Actual verification message is not as expected. \nActual: " + actVerificationText + "\nExpected: " + expVerificationText);
        } catch (NoSuchElementException e) {
            String actBrokenSiteErr;
            String expBrokenSiteErr;
            actBrokenSiteErr = driver.findElement(By.cssSelector("#rightPanel")).getAttribute("innerText");
            expBrokenSiteErr = """
                    Error!
                                        
                    The username and password could not be verified.""";
            if (actBrokenSiteErr.equals(expBrokenSiteErr)) {
                Assert.fail("Site is broken. Test failed.");
            }
        }
        // Close browser
        // Test
        driver.quit();
    }

    @Test(groups = {"positiveTests", "smokeTests"})
    public void contact() {
        System.out.println("Starting contact test");

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
        Assert.assertEquals(actVerificationText, expVerificationText, "Actual verification message is not as expected. \nActual: " + actVerificationText + "\nExpected: " + expVerificationText);

        // Close browser
        driver.quit();
    }

    @Test
    public void requestLoad() {
        System.out.println("Starting request load test");

        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://parabank.parasoft.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("ParaBank request load page opened.");

        String loanAmount = "";
        String downPayment = "";

        // Read from JSON
        try {
            paramsJsonParser = new JSONParser().parse(new FileReader("src/config/params.json"));
            paramsJsonObject = (JSONObject) paramsJsonParser;
            loanAmount += (String) paramsJsonObject.get("loanAmount");
            downPayment += (String) paramsJsonObject.get("downPayment");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        // Fill login fields, and click login
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input")).sendKeys((CharSequence) paramsJsonObject.get("username"));
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(4) > input")).sendKeys((CharSequence) paramsJsonObject.get("password"));
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input")).click();

        driver.get("https://parabank.parasoft.com/parabank/requestloan.htm");

        driver.findElement(By.id("amount")).sendKeys(loanAmount);
        driver.findElement(By.id("downPayment")).sendKeys(downPayment);
        driver.findElement(By.xpath("(//input[@type='submit'])")).click();

        // Verification
        sleep(); // Sleep for 1 second
        String actVerificationText = driver.findElement(By.xpath("(//div[@class='ng-scope'])[3]")).getAttribute("innerText");
        String expVerificationText = "Congratulations, your loan has been approved.";
        Assert.assertTrue(actVerificationText.contains(expVerificationText), "Actual verification message is not as expected. \nActual: " + actVerificationText + "\nExpected: " + expVerificationText);

        // Close browser
        driver.quit();
    }

    @Test
    public void responseTest() {
        try {
            URL url = new URL("https://parabank.parasoft.com/parabank/services/bank/accounts/13788");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Assert.assertEquals(con.getResponseCode(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
