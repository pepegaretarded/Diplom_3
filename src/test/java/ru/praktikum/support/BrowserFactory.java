package ru.praktikum.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;

public final class BrowserFactory {
    private BrowserFactory() { }

    public static WebDriver create(String browser) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000", "--no-first-run", "--no-default-browser-check");
        options.addArguments("--remote-debugging-pipe");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        String chromeBinary = System.getProperty("chrome.binary");
        if (chromeBinary != null && !chromeBinary.isBlank()) {
            options.setBinary(chromeBinary);
        }
        String driverPath = System.getProperty("chrome.driver");
        if ("yandex".equals(browser)) {
            options.setBinary(System.getProperty("yandex.binary",
                    "/Applications/Yandex.app/Contents/MacOS/Yandex"));
            driverPath = System.getProperty("yandex.driver", System.getProperty("user.home")
                    + "/.cache/diplom-drivers/yandexdriver");
        } else if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Supported browsers: chrome, yandex");
        }
        WebDriver driver;
        if (driverPath == null || driverPath.isBlank()) {
            driver = new ChromeDriver(options);
        } else {
            ChromeDriverService.Builder builder = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath)).usingAnyFreePort();
            if (Boolean.getBoolean("webdriver.log")) {
                builder.withVerbose(true).withLogFile(new File("target/" + browser + "-driver.log"));
            }
            ChromeDriverService service = builder.build();
            driver = new ChromeDriver(service, options);
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        return driver;
    }
}
