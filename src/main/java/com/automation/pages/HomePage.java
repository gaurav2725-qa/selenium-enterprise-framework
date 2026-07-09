package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger logger =
            LoggerFactory.getLogger(HomePage.class);

    // ── Locators ──────────────────────────────────────────────
    @FindBy(css = ".flash.success")
    private WebElement successMessage;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(css = "h2")
    private WebElement pageHeading;

    // ── Actions ───────────────────────────────────────────────
    @Step("Get success message")
    public String getSuccessMessage() {
        logger.info("Getting success message");
        return getText(successMessage);
    }

    @Step("Check if success message is displayed")
    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    @Step("Get page heading")
    public String getPageHeading() {
        return getText(pageHeading);
    }

    @Step("Click logout")
    public LoginPage logout() {
        logger.info("Logging out");
        click(logoutButton);
        return new LoginPage();
    }
}