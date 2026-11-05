package ru.praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Вход']");
    private final By emailInput = By.xpath("//input[@type='text']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registrationLink = By.linkText("Зарегистрироваться");
    private final By passwordRecoveryLink = By.linkText("Восстановить пароль");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Дождаться формы входа")
    public LoginPage waitUntilLoaded() {
        visible(heading);
        return this;
    }

    @Step("Войти в аккаунт")
    public MainPage login(String email, String password) {
        fill(emailInput, email);
        fill(passwordInput, password);
        click(loginButton);
        MainPage main = new MainPage(driver);
        main.isLoggedIn();
        return main;
    }

    @Step("Открыть форму регистрации")
    public RegistrationPage openRegistration() {
        click(registrationLink);
        return new RegistrationPage(driver).waitUntilLoaded();
    }

    @Step("Открыть восстановление пароля")
    public PasswordRecoveryPage openPasswordRecovery() {
        click(passwordRecoveryLink);
        return new PasswordRecoveryPage(driver).waitUntilLoaded();
    }

    @Step("Проверить открытие формы входа")
    public boolean isOpen() {
        visible(heading);
        return driver.getCurrentUrl().endsWith("/login");
    }
}
