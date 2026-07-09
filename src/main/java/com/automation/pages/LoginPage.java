package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginPage.class);

    // ── Locators ──────────────────────────────────────────────
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".flash.error")
    private WebElement errorMessage;

    // ── Actions ───────────────────────────────────────────────
    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameField, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        type(passwordField, password);
        return this;
    }

    @Step("Click login button")
    public HomePage clickLogin() {
        logger.info("Clicking login button");
        click(loginButton);
        return new HomePage();
    }

    @Step("Login with username: {username}")
    public HomePage loginAs(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }
}