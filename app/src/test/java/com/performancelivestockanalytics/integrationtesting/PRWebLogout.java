package com.performancelivestockanalytics.integrationtesting;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PRWebLogout implements Constants {
    private WebDriver driver;
    WebDriverWait wait;

    PRWebLogout (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEWAIT);
    }

    public void logOut() {
        // We need to remove any pop-ups, so by refreshing the page it will remove them and the account setting button will be visible
        driver.navigate().refresh();

        // After removing any pop-ups, navigate the user to the account setting where the log out button is located
        checkVisibilityOrScroll(wait.until(visibilityOfElementLocated(By.className("account-settings-glyphicon")))).click();

        // Since log out button does not have a specific id, search through the list of elements that has className as btn-primary
        List<WebElement> tempBtn = driver.findElements(By.className("btn-primary"));
        for (WebElement logout : tempBtn) {
            if (logout.getText().equals("Log Out")) {
                checkVisibilityOrScroll(logout).click();

                // Exit the loop to avoid stale element not found error
                break;
            }
        }

        /* Now confirm the logout. If successful, the assert will pass.
           Determine the success by the existence of username box in login page
           */
        assert(checkVisibilityOrScroll(driver.findElement(By.id("user"))).isDisplayed());
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
