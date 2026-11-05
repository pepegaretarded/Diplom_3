package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {
    private final By constructorHeading = By.xpath("//h1[text()='Соберите бургер']");
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountLink = By.xpath("//a[.//p[text()='Личный Кабинет']]");
    private final By placeOrderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By ingredientCards = By.cssSelector("a[href^='/ingredient/']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(BASE_URL + "/");
        visible(constructorHeading);
        visible(ingredientCards);
        return this;
    }

    public LoginPage openLoginByMainButton() {
        click(loginButton);
        return new LoginPage(driver).waitUntilLoaded();
    }

    public LoginPage openLoginByPersonalAccount() {
        click(personalAccountLink);
        return new LoginPage(driver).waitUntilLoaded();
    }

    public boolean isLoggedIn() {
        visible(placeOrderButton);
        return driver.findElement(placeOrderButton).isDisplayed();
    }

    public MainPage selectSection(String section) {
        click(sectionTab(section));
        // Smooth scrolling can briefly activate a tab before scrolling settles.
        long[] selectedSince = {0};
        wait.until(browser -> {
            if (!browser.findElement(sectionTab(section)).getAttribute("class")
                    .contains("tab_tab_type_current")) {
                selectedSince[0] = 0;
                return false;
            }
            if (selectedSince[0] == 0) {
                selectedSince[0] = System.nanoTime();
            }
            return System.nanoTime() - selectedSince[0] >= 1_000_000_000L;
        });
        return this;
    }

    public boolean isSectionSelected(String section) {
        return driver.findElement(sectionTab(section)).getAttribute("class")
                .contains("tab_tab_type_current");
    }

    private By sectionTab(String section) {
        return By.xpath("//div[contains(@class,'tab_tab__')][span[text()='" + section + "']]");
    }
}
