package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.linkText("Войти");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    public PasswordRecoveryPage waitUntilLoaded() {
        visible(heading);
        return this;
    }

    public LoginPage openLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilLoaded();
    }
}
