package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class PBWebEditAnimal implements Constants{
    private WebDriverWait wait;
    private WebDriver driver;

    PBWebEditAnimal(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }


    /**
     * Currently the date is fixed value
     * Can change it by changing the string value inside .sendkeys()
     */
    public void addWeight(String tagID, String targetGroup, String weight) {
        /* Here we need to check if the current view is at the Edit Group page,
           if not then direct the user to the corresponding page to add Weight
           This is because when the user wants to addWeight and addDosage, the user do not have to
           navigate from overview page and search for targetGroup (redundant)
           */
        if (!driver.findElement(By.className("page-title")).equals("Edit Group")) {
            // Not inside the edit group page
            navigateToHealthGroup(targetGroup);
        }

        // Navigate to edit animal page
        navigateToEditAnimal(tagID);

        // Add a row
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-link='{on addAnimalHistory}']")))).click();

        /* The last element inside the list is the new added animal history (weight) */
        List<WebElement> animalHistory = driver.findElements(By.cssSelector("[data-link*='pounds convertBack=~toFloat']"));

        // Find the weight input element
        WebElement weightBox = animalHistory.get(animalHistory.size() - 1);
        weightBox.sendKeys(weight);

        // Get the parent element's data-jsv value
        String parentDataJsvValue = weightBox.findElement(By.xpath("./ancestor::tr")).getAttribute("data-jsv");

        /* Now use the parentDataJsvValue to find the date input element + set as fixed date (for now)
           The date takes 6 inputs, so replace the first two as 0s
           */
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("tr[data-jsv='" + parentDataJsvValue + "'] input[type='date']"))))
                .sendKeys("002023-07-17");

        // Search for save changes button then click
        List<WebElement> saveChange = driver.findElements(By.className("button-text"));
        for (WebElement el : saveChange) {
            if (el.getText().trim().equals("Save Changes")) {
                el.click();
                break;
            }
        }
    }

    /**
     * Currently the date and treatment/item are fixed values
     * Can change it by changing the string value inside .sendkeys()
     */
    public void addDosage(String tagID, String targetGroup, String dosage) {
        /* Here we need to check if the current view is at the Edit Group page,
           if not then direct the user to the corresponding page to add Dosage
           This is because when the user wants to addWeight and addDosage, the user do not have to
           navigate from overview page and search for targetGroup (redundant)
           */
        if (!driver.findElement(By.className("page-title")).equals("Edit Group")) {
            // Not inside the edit group page
            navigateToHealthGroup(targetGroup);
        }

        // Navigate to edit animal page
        navigateToEditAnimal(tagID);

        // Add a row
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-link='{on addCost}']")))).click();

        // Open Treatment/Item filter
        WebElement treatmentBox = null; // Use to find the parent data-jsv value
        List<WebElement> treatmentItem = driver.findElements(By.className("filter-option-inner-inner"));
        for (WebElement el : treatmentItem) {
            // Newly added row has "Select Inventory" as the default value for Treatment/Item
            if (el.getText().trim().equals("Select Inventory")) {
                treatmentBox = el;
                el.click();
                break;
            }
        }

        // At this point the treatmentBox should not be null. Return error if it is
        assert(!Objects.isNull(treatmentBox));

        // Get the parent element's data-jsv value
        String parentDataJsvValue = treatmentBox.findElement(By.xpath("./ancestor::tr")).getAttribute("data-jsv");

        /* Now use the parentDataJsvValue to find the search box for treatment/item
           Then set the treatment as fixed value (for now)
           */
        WebElement treatmentSearchBox = checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector(
                "tr[data-jsv='" + parentDataJsvValue + "'] div.dropdown div.bs-searchbox input[aria-label='Search']"))));
        treatmentSearchBox.sendKeys("Pelvic Area");
        treatmentSearchBox.sendKeys(ENTER);

        // Set Dosage
        WebElement dosageBox = checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector(
                "tr[data-jsv='" + parentDataJsvValue + "'] div.col-xs-16 input[type='number']"))));
        dosageBox.sendKeys(dosage);

        // Set Date. It takes 6 inputs, so replace the first two as 0s
        WebElement dateBox = checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector(
                "tr[data-jsv='" + parentDataJsvValue + "'] input[type='date']"))));
        dateBox.sendKeys("002023-07-17");

        // Search for save changes button then click
        List<WebElement> saveChange = driver.findElements(By.className("button-text"));
        for (WebElement el : saveChange) {
            if (el.getText().trim().equals("Save Changes")) {
                el.click();
                break;
            }
        }
    }

    /**
     * Deleting an animal inside the targetGroup
     * @param tagID - to delete
     * @param targetGroup - where tagID is located
     */
    public void deleteAnimal(String tagID, String targetGroup) {
        navigateToHealthGroup(targetGroup);
        navigateToEditAnimal(tagID);

        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-target='#deleteAnimalModal']")))).click();

        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-link='{on ~root.removeAnimal}']")))).click();
    }


    public void navigateToHealthGroup(String targetGroup) {
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
    }


    /**
     * @param tagID - If the animal doesn't exists in side the group, the test will fail
     */
    public void navigateToEditAnimal(String tagID) {
        /* Already inside the targetGroup */

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

        /* Since the animal exists inside the targetGroup, now edit the animal */

        /* When the window size is small, the edit button disappears, so after clicking the "+" button next to Tag ID, the edit button should pop up */
        if (driver.findElement(By.className("edit-animal")).isDisplayed()) {
            checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("edit-animal")))).click();
        } else {
            // Search through the elements and click the element("+" button) that matches tagID
            List<WebElement> animalInGroup = driver.findElements(By.className("sorting_1"));
            for (WebElement el : animalInGroup) {
                if (el.getText().trim().equals(tagID)) {
                    el.click();

                    // Click the 2nd edit button that just appeared from previous click
                    driver.findElement(By.cssSelector("span.dtr-data button.edit-animal")).click();
                    break;
                }
            }
        }
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

