package com.automation.base;

import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;
import com.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static final Logger logger =
            LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        logger.info("========== TEST STARTING ==========");
        DriverManager.initDriver();
        driver = DriverManager.getDriver();

        String url = ConfigManager.getInstance().get("app.url");
        driver.get(url);
        logger.info("Navigated to: {}", url);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test FAILED: {}", result.getName());
            ScreenshotUtil.captureScreenshot(result.getName());
        }
        DriverManager.quitDriver();
        logger.info("========== TEST COMPLETE ==========");
    }
}