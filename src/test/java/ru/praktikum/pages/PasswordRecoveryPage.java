package ru.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginLink = By.linkText("Войти");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Дождаться восстановления пароля")
    public PasswordRecoveryPage waitUntilLoaded() {
        visible(heading);
        return this;
    }

    @Step("Перейти ко входу из восстановления пароля")
    public LoginPage openLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilLoaded();
    }
}
