package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.config.ConfigManager;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Login")
public class LoginTest extends BaseTest {

    @Test
    @Story("Valid Login")
    @Description("Verify user can login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        String username = ConfigManager.getInstance()
                .get("app.username");
        String password = ConfigManager.getInstance()
                .get("app.password");

        HomePage homePage = new LoginPage()
                .loginAs(username, password);

        Assert.assertTrue(
                homePage.isSuccessMessageDisplayed(),
                "Success message should be displayed after login"
        );
        Assert.assertTrue(
                homePage.getSuccessMessage()
                        .contains("You logged into a secure area!"),
                "Success message text does not match"
        );
    }

    @Test
    @Story("Invalid Login")
    @Description("Verify error message shown with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage()
                .enterUsername("wronguser")
                .enterPassword("wrongpass");

        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isErrorDisplayed(),
                "Error message should be displayed"
        );
    }

    @Test
    @Story("Valid Login")
    @Description("Verify user can logout successfully")
    @Severity(SeverityLevel.NORMAL)
    public void testLogout() {
        String username = ConfigManager.getInstance()
                .get("app.username");
        String password = ConfigManager.getInstance()
                .get("app.password");

        new LoginPage()
                .loginAs(username, password)
                .logout();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://the-internet.herokuapp.com/login",
                "Should redirect to login page after logout"
        );
    }
}