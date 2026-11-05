package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected static final String BASE_URL = System.getProperty("base.url",
            "https://stellarburgers.education-services.ru");
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest', behavior: 'instant'});",
                element);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void fill(By locator, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    }

    protected void visible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
