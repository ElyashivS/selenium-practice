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

    @BeforeSuite
    public void before_suite() {
        System.out.println("Starting set up");
        String driverLocation = "";
        // Read from JSON
        Object jp;
        try {
            jp = new JSONParser().parse(new FileReader("src/config/config.json"));
            JSONObject jo = (JSONObject) jp;
            driverLocation += (String) jo.get("chromeDriverLocation");
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
                Assert.fail("Site is broken. Test failed.");
            }
        }
        // Close browser
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
        Object jp;
        try {
            jp = new JSONParser().parse(new FileReader("src/config/params.json"));
            JSONObject jo = (JSONObject) jp;
            loanAmount += (String) jo.get("loanAmount");
            downPayment += (String) jo.get("downPayment");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        // Fill login fields, and click login
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(2) > input")).sendKeys("Extreme72");
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(4) > input")).sendKeys("qwe123");
        driver.findElement(By.cssSelector("#loginPanel > form > div:nth-child(5) > input")).click();

        driver.get("https://parabank.parasoft.com/parabank/requestloan.htm");

        driver.findElement(By.id("amount")).sendKeys(loanAmount);
        driver.findElement(By.id("downPayment")).sendKeys(downPayment);
        driver.findElement(By.xpath("(//input[@type='submit'])")).click();

        // Verification
        sleep(1000);
        String actVerificationText = driver.findElement(By.xpath("(//div[@class='ng-scope'])[3]"))
                .getAttribute("innerText");
        String expVerificationText = "Congratulations, your loan has been approved.";
        Assert.assertTrue(actVerificationText.contains(expVerificationText),
                "Actual verification message is not as expected. \nActual: "
                        + actVerificationText + "\nExpected: "
                        + expVerificationText);

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

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
