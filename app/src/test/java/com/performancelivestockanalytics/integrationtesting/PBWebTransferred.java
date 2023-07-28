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

    PBWebTransferred (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Check for the animal name inside the Group that the animal was added to.
     * To view the transferred animal, it is located in group inside the Health tab
     */
    public void checkTransferred(String tagID, String targetGroup) {
        // We need to remove any pop-ups, so by refreshing the page it will remove them
        driver.navigate().refresh();

        // Click the Health icon
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("nav-health"))))
                .click();

        // Wait for the Health page to load
        wait.until(visibilityOfElementLocated(By.className("component-name")));

        // Since the button for targetGroup does not have a specific id, search through the list of elements that contains all the group name
        List<WebElement> group = driver.findElements(By.className("component-name"));
        for (WebElement el : group) {
            if (el.getText().equals(targetGroup)) {
                el.click();
                break;
            }
        }

        // Enter the animalName in the search bar
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[aria-controls='DataTables_Table_0']"))))
                .sendKeys(tagID);

        /* Now confirm the existence of the animal. If successful, the assert will pass.
           Determine the success by the comparing the animal name that was searched to the animalName (param)
           */
        List<WebElement> tagIDList = driver.findElements(By.className("sorting_1"));
        Boolean didAppear = false;
        for (WebElement el : tagIDList) {
            // There are additional spaces for Tag Id within the animal names
            if (el.getText().trim().equals(tagID)) {
                didAppear = true;
                break;
            }
        }

        assert(didAppear);
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
