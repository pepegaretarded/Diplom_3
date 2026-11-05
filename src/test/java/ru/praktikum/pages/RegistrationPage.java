package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {
    private final By heading = By.xpath("//h2[text()='Регистрация']");
    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.linkText("Войти");
    private final By passwordError = By.xpath("//p[text()='Некорректный пароль']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public RegistrationPage waitUntilLoaded() {
        visible(heading);
        return this;
    }

    public void register(String name, String email, String password) {
        fill(nameInput, name);
        fill(emailInput, email);
        fill(passwordInput, password);
        click(registerButton);
    }

    public boolean hasPasswordError() {
        visible(passwordError);
        return driver.findElement(passwordError).isDisplayed();
    }

    public LoginPage openLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilLoaded();
    }

    public LoginPage waitForSuccessfulRegistration() {
        return new LoginPage(driver).waitUntilLoaded();
    }
}
