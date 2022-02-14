package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {

    @Test(groups = {"negativeTests", "firstNameTest"})
    public void emptyFirstName() {
        System.out.println("Starting Empty First Name test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://qualitestgroup.com/contact-us/";
        driver.get(url);

        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Contact Us page opened.");

        // Fill Last Name, Company name, Phone, Email and Location
        WebElement lastNameElement = driver.findElement(By.id("lastname-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        lastNameElement.sendKeys("Someone last name");
        WebElement companyNameElement = driver.findElement(By.id("company-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        companyNameElement.sendKeys("Someone company");
        WebElement phoneNumberElement = driver.findElement(By.id("phone-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        phoneNumberElement.sendKeys("0500000000");
        WebElement careerTalkAbtElement = driver.findElement(By.id("what_would_you_like_to_talk_about_1-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        careerTalkAbtElement.click();
        WebElement emailElement = driver.findElement(By.id("secondary_email__c-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        emailElement.sendKeys("A7X@gmail.com");
        WebElement locationAsiaElement = driver.findElement(By.xpath("(//option[@value='Asia'])"));
        locationAsiaElement.click();

        // Sleep for 2 seconds
        sleep(2000);

        // Click SEND REQUEST
        WebElement sendReqElement = driver.findElement(By.cssSelector(".hs-button.primary.large"));
        sendReqElement.click();


        // Verifications
        WebElement firstNameVerString = driver.findElement(By.xpath("(//label[@class='hs-error-msg'])[1]"));
        String firNameActErr = firstNameVerString.getText();
        String firNameExpErr = "Please complete this required field.";
        Assert.assertTrue(firNameActErr.contains(firNameExpErr),
                "Actual error message does not contain expected. \nActual: "
                        + firNameActErr + "\nExpected: "
                        + firNameExpErr);

        // Sleep for 3 seconds
        sleep(3000);

        driver.quit();
    }

    @Test(groups = {"negativeTests", "phoneNumberTest"})
    @Parameters({"phoneNumber"})
    public void invalidPhoneNumber(String phoneNumber) {
        System.out.println("Starting invalid phone number test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://qualitestgroup.com/contact-us/";
        driver.get(url);

        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Contact Us page opened.");

        // Fill First Name, Last Name, Company name, Email and Location
        WebElement firstNameElement = driver.findElement(By.id("firstname-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        firstNameElement.sendKeys("Someone first name");
        WebElement lastNameElement = driver.findElement(By.id("lastname-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        lastNameElement.sendKeys("Someone last name");
        WebElement companyNameElement = driver.findElement(By.id("company-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        companyNameElement.sendKeys("Someone company");
        WebElement careerTalkAbtElement = driver.findElement(By.id("what_would_you_like_to_talk_about_1-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        careerTalkAbtElement.click();
        WebElement emailElement = driver.findElement(By.id("secondary_email__c-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        emailElement.sendKeys("A7X@gmail.com");
        WebElement locationAsiaElement = driver.findElement(By.xpath("(//option[@value='Asia'])"));
        locationAsiaElement.click();

        // Fill invalid phone number
        WebElement phoneNumberElement = driver.findElement(By.id("phone-34dd68e0-b077-4e95-9243-b861f3f2fd7d"));
        phoneNumberElement.sendKeys(phoneNumber); // The invalid input is "aaa", and then "bbb"


        // Click SEND REQUEST
        WebElement sendReqElement = driver.findElement(By.cssSelector(".hs-button.primary.large"));
        sendReqElement.click();

        // Verifications
        WebElement phoneNumVerString = driver.findElement(By.xpath("(//label[@class='hs-error-msg'])[1]"));
        String phoneNumActErr = phoneNumVerString.getText();
        String phoneNumExpErr = "Must contain only numbers, +()-. and x.";
        Assert.assertTrue(phoneNumActErr.contains(phoneNumExpErr),
                "Actual error message does not contain expected. \nActual: "
                        + phoneNumActErr + "\nExpected: "
                        + phoneNumExpErr);

        // Sleep for 3 seconds
        sleep(3000);

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
