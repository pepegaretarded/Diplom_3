package ru.praktikum.support;

import io.qameta.allure.Allure;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.praktikum.client.StellarApi;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public abstract class UiTestBase {
    protected final String browser;
    protected WebDriver driver;
    protected StellarApi api;
    protected TestUser user;

    protected UiTestBase(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "browser={0}")
    public static Collection<Object[]> browsers() {
        return Arrays.stream(System.getProperty("browsers", "chrome,yandex").split(","))
                .map(String::trim).map(name -> new Object[] {name}).collect(Collectors.toList());
    }

    @Before
    public void setUp() {
        api = new StellarApi();
        user = new TestUser(api);
        driver = BrowserFactory.create(browser);
        Allure.parameter("Браузер", browser);
        Allure.parameter("Версия", ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
    }

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                Allure.addAttachment("Финальное состояние: " + browser, "image/png",
                        new ByteArrayInputStream(((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.BYTES)), ".png");
            }
        } finally {
            try {
                if (driver != null) {
                    driver.quit();
                }
            } finally {
                if (user != null) {
                    user.close();
                }
            }
        }
    }
}
