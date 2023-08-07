package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.apache.tools.ant.taskdefs.Java;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PRWebTransferAnimal implements Constants{
    private WebDriver driver;
    WebDriverWait wait;

    PRWebTransferAnimal (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    public void transferAnimal(String tagID, String group, String pen) {
        new PRWebNavigate(driver).navigateToOverview();

        // Search the animal
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("groupSearch")))).sendKeys(tagID);

        /* Checkmark and tagId shares the same parent <tr>, so use that to find the checkmark */
        List<WebElement> animal = driver.findElements(By.cssSelector("[style='white-space: pre;']"));
        for (WebElement el : animal) {
            // Find the tagID
            if (el.getText().trim().equals(tagID)) {
                // Find the parent element of tagID
                WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].closest('tr')", el);

                // Click the checkmark within the parent <tr> element
                parent.findElement(By.cssSelector("span.custom-checkmark")).click();
                break;
            }
        }

        // Now click the depart button that just appeared from checking the checkmark
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[data-el='departAnimalButton']")))).click();

        // Search for the transfer element then click
        List<WebElement> tab = driver.findElements(By.cssSelector("[role='tab']"));
        for (WebElement el : tab) {
            if (el.getAttribute("textContent").trim().equals("Transfer")) {
                /* Just by calling el.click(), it will give element not interactable error
                   So need to wait for a little bit for it to be interacted
                   */
                checkVisibilityOrScroll(el).click();
                break;
            }
        }

        // Set departure date - "2023-07-15"
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.id("departureDateTransfer")))).sendKeys("2023-07-15");

        /* Get all elements are inside the list of Group
           then find the one that matches the parameter group
           */
        List<WebElement> inputGroup = driver.findElements(By.className("input-group-addon"));
        for (WebElement el : inputGroup) {
            if (el.getText().trim().equals("*Group")) {
                // Getting the parent of the neighbor element
                WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].closest('div')", el);
                parent.findElement(By.cssSelector("select.form-control")).click();

                List<WebElement> groupList = driver.findElements(By.cssSelector("[value='[object Object]']"));
                for (WebElement el2 : groupList) {
                    if (el2.getText().trim().equals(group)) {
                        // Select the group
                        el2.click();

                        // Close the dropdown because above click does click the group but doesn't close the dropdown
                        el.click();
                        break;
                    }
                }
            }
        }

        // Set Cattle Condition - Green
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.cssSelector("[value='green']")))).click();

        /* Set Pen - hard coded to Pen0128
           Use the inputGroup list to find the *Pen element to sendkeys (@param pen)
           */
        for (WebElement el : inputGroup) {
            if (el.getText().trim().equals("*Pen")) {
                WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].closest('div')", el);

                parent.findElement(By.cssSelector("select.form-control")).sendKeys(pen);
                break;
            }
        }

        // Finding the create and transfer button by id doesn't work so also find inside the lists of btn
        List<WebElement> btn = driver.findElements(By.className("btn"));
        WebElement createTransfer = null;
        for (WebElement el : btn) {
            if (el.getText().trim().equals("Create and Transfer")) {
                createTransfer = el;
                el.click();
                break;
            }
        }

        // Need to wait else below assertion will fail even though the animal was transferred
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            /* Here we check whether the animal transferred successfully by the
               existence of create and transfer button
               */
            assert(!createTransfer.isDisplayed());
        } catch (StaleElementReferenceException e) {
            // Animal transferred successfully
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
