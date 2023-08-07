package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PBWebAddHead implements Constants{
    private WebDriver driver;
    WebDriverWait wait;

    PBWebAddHead (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }


    public void addHeadToNewGroup(String targetGroup) {
        /* Hard coded values
         * Date:             2023-07-16
         * Rate of Grain:    3.2
         * Management Fee:   0.75
         * Interest:         2
         * Breed:            Wagyu
         * Gender:           Cow
         * Head Count:       100
         * Trucking Cost:    1000
         * Source:           D-Source
         * Origin:           Indiana
         * Cattle Condition: Green
         * Pen:              D-Pen
         * Owner:            test Owner
         * Purchase Cost Total:   1557
         * Purchase Weight Total: 7777
         */

        // Since the add animal button may not be displayed in current page, navigate to the overview page
        new PBWebNavigate(driver).navigateToOverview();

        // Click +/- Head
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("btn_head_change")))).click();

        // Click Added tab
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-value='added']")))).click();

        // Open the Group ID dropdown menu then select a new group as targetGroup
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("group")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-value='new']")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("groupNew")))).sendKeys(targetGroup);

        // Set date 2023-1-16
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("date")))).sendKeys("2023-07-16");

        // Set Purchase Cost Total
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("cost")))).sendKeys("1557");

        // Set Purchase Weight Total
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("purchaseWeight")))).sendKeys("7777");

        // Set Trucking Cost
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("truckingCost")))).sendKeys("1000");

        // Open the Source dropdown menu then select Source as D-Source
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("source")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-value='D-Source']")))).click();

        // Set the Origin State - Indiana
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("origin")))).sendKeys("Indiana");

        // Set Rate of Gain - 3.2
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("rateOfGain")))).sendKeys("3.2");

        // Set Management Fee - 0.75
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("management")))).sendKeys("0.75");

        // Set Interest - 2
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("interest")))).sendKeys("2");

        // Set Cattle Condition - Green
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("cattleCondition")))).sendKeys("Green");

        // Open Breed dropdown then select "Wagyu", currently it does not click the checkbox
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("breedButton")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-label='Wagyu']")))).click();

        // Open Gender dropdown then select gender as "Cow"
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("genderButton")))).click();
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-label='Cow']")))).click();

        // Set Pen Select - D-Pen
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("penDropdown")))).sendKeys("D-Pen");

        // Sometimes the head doesn't get updated for Pen, so setting Head Count after selecting Pen
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("numHead")))).sendKeys("100");

        // Set Group Ownership - test Owner
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("owner")))).sendKeys("test Owner");

        // Add a note
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("notes")))).sendKeys("Added using selenium + To a New Group");

        // Click Preview by calling searchList
        searchList(driver.findElements(By.className("btn-confirm")), "Preview").click();

        // Click Confirm button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("confirm_head_change")))).click();


        // Click OK button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("btn-default")))).click();
    }


    public void addHeadToExistingGroup() {
        /* Hard coded values
         * Group:            D-Group
         * Date:             2023-07-16
         * Head Count:       100
         * Trucking Cost:    1000
         * Source:           D-Source
         * Origin:           Indiana
         * Cattle Condition: Green
         * Pen:              D-Pen
         * Owner:            test Owner
         * Purchase Cost Total:   1557
         * Purchase Weight Total: 7777
         */

        // Since the add animal button may not be displayed in current page, navigate to the overview page
        new PBWebNavigate(driver).navigateToOverview();

        // Click +/- Head
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("btn_head_change")))).click();

        // Click Added tab
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-value='added']")))).click();

        // Open the Group ID dropdown menu
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("group")))).click();

        // Select Group ID - D-Group
        WebElement dropdownMenu = checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[onchange='checkForGroupType()']"))));
        dropdownMenu.findElement(By.xpath(".//*[contains(text(), '" + "D-Group" + "')]")).click();

        // Set date 2023-1-16
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("date")))).sendKeys("2023-07-16");

        /* Set Head after selecting Pen because sometimes the head doesn't get updated when selecting Pen */

        // Set Purchase Cost Total
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("cost")))).sendKeys("1557");

        // Set Purchase Weight Total
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("purchaseWeight")))).sendKeys("7777");

        // Set Trucking Cost
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("truckingCost")))).sendKeys("1000");

        // Open the Source dropdown menu
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("source")))).click();

        // Select D-Source
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-value='D-Source']")))).click();

        // Open the Origin State
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("origin")))).sendKeys("Indiana");

        // Set Cattle Condition - Green
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("cattleCondition")))).sendKeys("Green");

        // Set Pen Select - D-Pen
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("penDropdown")))).sendKeys("D-Pen");

        // Set Head Count - 100
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("numHead")))).sendKeys("100");

        // Set Group Ownership - test Owner
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("owner")))).sendKeys("test Owner");

        // Add a note
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("notes")))).sendKeys("Added using selenium + Existing Group");

        // Click Preview by calling searchList
        searchList(driver.findElements(By.className("btn-confirm")), "Preview").click();

        // Click Confirm button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("confirm_head_change")))).click();


        // Click OK button
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("btn-default")))).click();
    }


    /**
     * Search through the list and find an element
     */
    public WebElement searchList(List<WebElement> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().equals(target)) {
                return list.get(i);
            }
        }
        return null;
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
