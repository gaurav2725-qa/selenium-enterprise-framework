package com.automation.utils;

import com.automation.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(ScreenshotUtil.class);

    private static final String SCREENSHOT_DIR = "screenshots/";

    private ScreenshotUtil() {}

    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.warn("Driver is null — cannot take screenshot");
            return null;
        }

        try {
            // Create directory if it doesn't exist
            Path dir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // Generate unique filename with timestamp
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd_HH-mm-ss"));
            String fileName = SCREENSHOT_DIR
                    + testName + "_" + timestamp + ".png";

            // Take and save screenshot
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fileName));

            logger.info("Screenshot saved: {}", fileName);
            return fileName;

        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}",
                    e.getMessage());
            return null;
        }
    }
}