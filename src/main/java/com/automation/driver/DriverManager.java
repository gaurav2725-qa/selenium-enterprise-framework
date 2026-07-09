package com.automation.driver;

import com.automation.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class DriverManager {

    private static final Logger logger =
            LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> driverThread =
            new ThreadLocal<>();

    private DriverManager() {
        // Prevent instantiation — static utility class
    }

    public static void initDriver() {
        String browser = ConfigManager.getInstance()
                .get("browser", "chrome").toLowerCase();
        boolean headless = ConfigManager.getInstance()
                .getBoolean("headless", false);
        int pageLoadTimeout = ConfigManager.getInstance()
                .getInt("timeout.page.load", 30);

        logger.info("Initialising {} driver | headless={}",
                browser, headless);

        WebDriver driver = createDriver(browser, headless);
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        driverThread.set(driver);
        logger.info("Driver initialised successfully");
    }

    private static WebDriver createDriver(String browser,
                                          boolean headless) {
        return switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                yield new ChromeDriver(options);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("--headless");
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver();
            }
            default -> throw new RuntimeException(
                    "Unsupported browser: " + browser);
        };
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            logger.info("Quitting driver");
            driver.quit();
            driverThread.remove();
        }
    }
}