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

    public void addAnimalTransfer(String birthDate, String visualID,  String gender, String breed) {
        // Click Add animal button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='addAnimalButton']")))).click();

        // Set Birthdate
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_date']")))).sendKeys(birthDate);

        // Set Visual ID
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='primary_visual_id']")))).sendKeys(visualID);

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


    public void addCalf(String birthDate, String visualID, String eID, String brandTattoo, String vigor, String gender, String origin,
                        String herd, String pasture, String sireID, String damID, String damBcsBirth, String breed, String color,
                        String hornedStatus, String birthWeight, String birthPasture, String managementCode, String calvingEase, String notes) {
        // Click Add animal button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='addAnimalButton']")))).click();

        // Set Birthdate
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_date']")))).sendKeys(birthDate);

        // Set Visual ID
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='primary_visual_id']")))).sendKeys(visualID);

        // Set EID
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='primary_tag_id']")))).sendKeys(eID);

        // Set Brand/Tattoo
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='brand']")))).sendKeys(brandTattoo);

        // Set Vigor
        /* The vigor is selected by sending the exact string value listed in the dropdown or by keyboards. But using keyboards may result in incorrect
           output when the list gets changed.
           */
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_vigor']")))).sendKeys(vigor);

        // Set Gender - applies same thing as Vigor
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='gender']")))).sendKeys(gender);

        // Set Origin - default value is "Raised"
        // Need to check what happens if empty string is passed to origin (Does "Raised" remain the same?)
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='origin']")))).sendKeys(origin);

        // Set Herd
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='herd_id']")))).sendKeys(herd);

        // Set Pasture
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='pasture_id']")))).sendKeys(pasture);

        // Set Sire ID
        /* Since we search for the Sire ID passed in as a param, no need to search for it
           Also, searching an empty string still shows the unfiltered list so only search when it is not empty
           */
        if (!sireID.isEmpty()) {
            driver.findElement(By.id("setSire")).click();
            checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='searchBox']")))).sendKeys(sireID);
            checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("sire-row-pair")))).click();

            /* When the search Sire is clicked, the set Sire button's text changes from "Set as Sire to" to "Set (sireID) as Sire to"
               The assert will pass if the Sire ID has been successfully searched and clicked
               */
            WebElement setSire = driver.findElement(By.id("modal-select-button-sire"));
            assert(setSire.getText().trim().equals("Set " + sireID + " as Sire to"));
            setSire.click();
        }

        // Set Dam ID
        /* Since we search for the Dam ID passed in as a param, no need to search for it
           Also, searching an empty string still shows the unfiltered list so only search when it is not empty
           */
        if (!damID.isEmpty()) {
            driver.findElement(By.id("setDam")).click();

            // The html is same with Sire search bar so accessing the element through the parent
            checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("changeDamModal"))))
                    .findElement(By.className("modal-dialog"))
                            .findElement(By.className("modal-content"))
                                    .findElement(By.className("modal-body"))
                                            .findElement(By.className("modal-header"))
                                                    .findElement(By.className("search-form"))
                                                            .findElement(By.cssSelector("[aria-label='Search']")).sendKeys(damID);

            checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("dam-row-pair")))).click();

            /* When the search Dam is clicked, the set Dam button's text changes from "Set as Dam to" to "Set (damID) as Dam to"
               The assert will pass if the Dam ID has been successfully searched and clicked
               Since there are duplicate elements for the set button id, search through them that contains "Dam"
               */
            List<WebElement> dam = driver.findElements(By.id("modal-select-button"));
            for (WebElement el : dam) {
                if (el.getText().contains("Dam")) {
                    assert(el.getText().trim().equals("Set " + damID + " as Sire to"));
                    el.click();
                    break;
                }
            }
        }

        // Set Dam BCS at Birth
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='dam_body_condition']")))).sendKeys(damBcsBirth);

        // Set Breed
        /* Search for Breed(param)
           When the window size is small such as 500 * 600, the list does not show up so need to search for the breed then press enter
           */
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("setBreed")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("btn-selectpicker")))).click();
        List<WebElement> tempBreed = driver.findElements(By.cssSelector("div.bs-searchbox > input.form-control[aria-label=\"Search\"]"));
        for (WebElement el : tempBreed) {
            if (el.getAttribute("aria-label").equals("Search")) {
                el.sendKeys(breed);
                el.sendKeys(Keys.ENTER);

                // This click is to close the edit breed list box
                driver.findElement(By.className("col-sm-6")).click();
            }
        }

        // Set Color
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='coat_color']")))).sendKeys(color);

        // Set Horned Status
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='horned_status']")))).sendKeys(hornedStatus);

        // Set Birth Weight
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_weight']")))).sendKeys(birthWeight);

        // Set Birth Pasture
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_pasture_id']")))).sendKeys(birthPasture);

        // Set Management Code
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='management_code']")))).sendKeys(managementCode);

        // Set Calving Ease
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='birth_assistance']")))).sendKeys(calvingEase);

        // Click Save, calling by className("btn-success") clicks something else
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("div.row.padding-x-xs > button.btn.btn-success.padding-y-lg")))).click();

        // Add notes , Send keys
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("animal-notes")))).sendKeys(notes);

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
