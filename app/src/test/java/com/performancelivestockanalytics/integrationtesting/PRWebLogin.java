package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PRWebLogin implements LoginInterface, Constants{

    private WebDriverWait wait;
    private WebDriver driver;

    PRWebLogin() {
        this.setUp();
    }

    @Override
    public void setUp() {
        /* This setup creates a chromedriver instead of using Safari because chromedriver
           can be both used in mac + window environment. Also setting the driver to small
           window size inorder to test all cases that users may experience.
           */
        //WebDriverManager.chromedriver().setup();

        /* Currently 2023-08-01 there are some issues with setting up the chromedriver
           So below ChromeOptions is a workaround for Selenium
           */
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(500, 600));

        // Use this WebDriverWait to make sure the driver is not trying to access an element that
        // is not yet accessible within the view i.e. loading
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    @Override
    public void tearDown() {
        driver.quit();
    }


    @Override
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Login function for Performance Ranch Web
     */
    @Override
    public void login(String targetServer, String username, String password) {
        // Pass in the targetServer to make the driver load the URL
        driver.get(targetServer);

        // Wait for the targetServer to load up then pass in the username and password
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("user"))))
                .sendKeys(username);
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("pass"))))
                .sendKeys(password);

        // After username and password has been inserted, move on by clicking login button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("login"))))
                .click();

        // Check for the Account button
        assert(checkVisibilityOrScroll(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("accountButton"))))).isDisplayed());

    }

    private WebElement checkVisibilityOrScroll(WebElement element) {
        if (element.isDisplayed()) {
            return element;
        } else {
            // The element is not displayed in current view, so scroll to that element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);

            return wait.until(ExpectedConditions.visibilityOf(element));
        }
    }
}
