package ru.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.pages.MainPage;
import ru.praktikum.pages.RegistrationPage;
import ru.praktikum.support.UiTestBase;
import ru.praktikum.support.TestUser;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends UiTestBase {
    public RegistrationTest(String browser) {
        super(browser);
    }

    @Test
    @DisplayName("Успешная регистрация с паролем из шести символов")
    public void userCanRegister() {
        RegistrationPage registration = new MainPage(driver).open().openLoginByMainButton()
                .openRegistration();
        user.expectUiRegistration();
        step("Заполнить регистрацию и отправить форму", () ->
                registration.register(user.name(), user.email(), user.password()));
        assertTrue("После регистрации должна открыться форма входа",
                registration.waitForSuccessfulRegistration().isOpen());
        api.login(user.credentials()).then().statusCode(200)
                .body("success", equalTo(true)).body("user.email", equalTo(user.email()));
    }

    @Test
    @DisplayName("Пароль короче шести символов отклоняется")
    public void shortPasswordShowsError() {
        user = new TestUser(api, 5);
        user.expectUiRegistration();
        RegistrationPage registration = new MainPage(driver).open().openLoginByMainButton()
                .openRegistration();
        step("Зарегистрироваться с паролем из пяти символов", () ->
                registration.register(user.name(), user.email(), user.password()));
        assertTrue("Должно появиться сообщение о некорректном пароле",
                registration.hasPasswordError());
    }
}
