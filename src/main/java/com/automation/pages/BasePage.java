package com.automation.pages;

import com.automation.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {

    private static final Logger logger =
            LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait   = new WebDriverWait(
                driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    // ── Core Actions ──────────────────────────────────────────

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.info("Clicking element: {}", element);
        element.click();
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '{}' into element", text);
    }

    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ── Wait Utilities ────────────────────────────────────────

    protected WebElement waitForElement(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementToDisappear(By locator) {
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ── JavaScript Utilities ──────────────────────────────────

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);",
                        element);
    }

    protected void clickWithJS(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
        logger.info("JS click on element: {}", element);
    }

    // ── Page Utilities ────────────────────────────────────────

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}