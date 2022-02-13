package test.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test(groups = {"positiveTests", "initTest"})
    public void contactUsClick() {
        System.out.println("Starting Contact Us Click test");

        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open test page
        String url = "https://qualitestgroup.com/";
        driver.get(url);
        // Maximize browser window
        driver.manage().window().maximize();
        System.out.println("Qualitest main page opened.");

        // Click Contact Us
        WebElement contactUsElement = driver.findElement(By.className("main-nav-btn"));
        contactUsElement.click();

        // Sleep for 1 second
        sleep(1000);
        System.out.println("Contact Us page opened.");

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
