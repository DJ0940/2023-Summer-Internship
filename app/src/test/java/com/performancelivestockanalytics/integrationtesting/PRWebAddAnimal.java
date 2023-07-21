package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PRWebAddAnimal {
    private static final int TIMEWAIT = 3; // Normal timeout waiting for components
    private WebDriver driver;
    WebDriverWait wait;

    PRWebAddAnimal(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    /**
     * Add an animal with minimum requirements for it to be transferred to Performance Beef
     * Role is fixed to Calf
     *
     * @param animalName (Visual ID)
     * @param birthDate  (year-month-date)
     * @param gender     (Steer Bull Heifer)
     * @param breed      (Just one breed as 100%)
     */

    public void addAnimalTransfer(String animalName, String birthDate, String gender, String breed) {
        // Click Add animal button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='addAnimalButton']")))).click();

        // Set Birthdate
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_date']")))).sendKeys(birthDate);

        // Set Visual ID
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='primary_visual_id']")))).sendKeys(animalName);

        // Set Gender
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='gender']")))).sendKeys(gender);

        // Click Breed - edit button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("setBreed")))).click();

        // Click the Breed Edit filter spinner
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("btn-selectpicker")))).click();

        // Search for Breed(param)
        // When the window size is small such as 500 * 600, the list does not show up so need to search for the breed then press enter
        List<WebElement> tempBreed = driver.findElements(By.cssSelector("div.bs-searchbox > input.form-control[aria-label=\"Search\"]"));
        for (WebElement el : tempBreed) {
            if (el.getAttribute("aria-label").equals("Search")) {
                el.sendKeys(breed);
                el.sendKeys(Keys.ENTER);

                // This click is to close the edit breed list box
                driver.findElement(By.className("col-sm-6")).click();
            }
        }

        // Click Save, calling by className("btn-success") clicks something else
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("div.row.padding-x-xs > button.btn.btn-success.padding-y-lg")))).click();

        // Add notes , Send keys
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("animal-notes")))).sendKeys("Added from Web-Selenium");

        // Click Add Animal
        driver.findElement(By.id("addAnimalButton")).click();

        // Refresh the page to load the added animal
        // When there is an existing animal, it will not add it
        driver.navigate().refresh();
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
