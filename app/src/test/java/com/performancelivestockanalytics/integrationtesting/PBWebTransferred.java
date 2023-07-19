package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PBWebTransferred {
    private static final int TIMEWAIT = 3; // Normal timeout waiting for components
    private WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    /**
     * Constructor
     */
    PBWebTransferred (WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) this.driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Check for the animal name inside the Group that the animal was added to
     * In this case, Group "D-Group" is the destination (Currently Hardcoded)
     * Navigates from Health tab -> targetGroup -> animalName
     */
    public void checkTransferred(String animalName, String targetGroup) {
        // Click the Health icon
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("nav-health"))))
                .click();

        // Wait for the Health page to load
        wait.until(visibilityOfElementLocated(By.className("component-name")));

        // Click D-Group
        List<WebElement> group = driver.findElements(By.className("component-name"));
        for (WebElement el : group) {
            if (el.getText().equals(targetGroup)) {
                el.click();
                break;
            }
        }

        // Search for the animal name
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[aria-controls='DataTables_Table_0']"))))
                .sendKeys(animalName);

        // Check if the animal name shows up
        assert(checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("sorting_1")))).getText()
                .equals(animalName));
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
