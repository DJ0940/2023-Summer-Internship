package com.performancelivestockanalytics.integrationtesting;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebLogin {

    private static final int TIMEWAIT = 3;
    private WebDriverWait wait;
    private WebDriver driver;
    // Constructor
    WebLogin() {
        this.setUp();
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(500, 600));
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Login function for Performance Beef Web
     */
    public void webLogin(String url, String username, String password) {
        // Navigate to the url (Beef or Ranch)
        driver.get(url);

        // Pass in the username and password
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("user")))))
                .sendKeys(username);
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("pass")))))
                .sendKeys(password);

        // Login
        //driver.findElement(By.className("login")).click();
        checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("login")))))
                .click();

        // Check if login was successful , this may return stale element error because the element cannot be reached after logging in
        assert(driver.findElement(By.id("user")).isDisplayed());
    }

    /**
     * Check if the element is visible, if not scroll to that element and return it
     *
     * Need to check if this function is working correctly
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
