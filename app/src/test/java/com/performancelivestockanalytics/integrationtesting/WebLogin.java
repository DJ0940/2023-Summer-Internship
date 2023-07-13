package com.performancelivestockanalytics.integrationtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Web Login Abstract class
 */
public class WebLogin {
    private static final int TIMEWAIT = 3;
    private WebDriverWait wait;
    private WebDriver driver;

    /**
     * Constructor
     * @return driver
     */
    public WebLogin(WebDriver driver) {
        this.driver = driver;
        this.setUp();
    }

    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(500, 600));
        wait = new WebDriverWait(driver, TIMEWAIT);
    }
    
    public void tearDown() {
        driver.quit();
    }

    /**
     * Return the driver
     */
    public WebDriver returnDriver() {
        return driver;
    }

    /**
     * Login function for Performance Beef Web
     */
    public void login(String url, String username, String password) {
        // Navigate to the url (Beef or Ranch)
        driver.get(url);

        // Pass in the username and password
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("user")))))
                .sendKeys(username);
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("pass")))))
                .sendKeys(password);

        // Click login button
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("login")))))
                .click();

        // Wait until the login was successful and navigated to the default view
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("account"))));

        // Return errors when the username for login page is still visible (login not successful)
        try {
            driver.findElement(By.id("user"));
        }
        catch (NoSuchElementException nse) {
            // Success
        }
    }

    /**
     * Check if the element is visible, if not scroll to that element and return it
     */
    private WebElement checkVisibilityOrScroll(WebElement element) {
        if (element.isDisplayed()) {
            return element;
        } else {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            return wait.until(ExpectedConditions.visibilityOf(element));
        }
    }
}
