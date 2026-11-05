package ru.praktikum.tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.pages.LoginPage;
import ru.praktikum.pages.MainPage;
import ru.praktikum.support.UiTestBase;

import static io.qameta.allure.Allure.step;
import static org.junit.Assert.assertTrue;

public class LoginTest extends UiTestBase {
    public LoginTest(String browser) {
        super(browser);
    }

    @Before
    public void createUserViaApi() {
        // UiTestBase.@After deletes this user and checks that login returns 401.
        user.create();
    }

    @Test
    @DisplayName("Вход по кнопке на главной странице")
    public void loginFromMainPage() {
        assertSuccessfulLogin(new MainPage(driver).open().openLoginByMainButton());
    }

    @Test
    @DisplayName("Вход через личный кабинет")
    public void loginFromPersonalAccount() {
        assertSuccessfulLogin(new MainPage(driver).open().openLoginByPersonalAccount());
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    public void loginFromRegistrationPage() {
        assertSuccessfulLogin(new MainPage(driver).open().openLoginByMainButton()
                .openRegistration().openLogin());
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    public void loginFromPasswordRecoveryPage() {
        assertSuccessfulLogin(new MainPage(driver).open().openLoginByMainButton()
                .openPasswordRecovery().openLogin());
    }

    @Step("Проверить успешный вход")
    private void assertSuccessfulLogin(LoginPage loginPage) {
        MainPage main = step("Войти под созданным через API пользователем", () ->
                loginPage.login(user.email(), user.password()));
        assertTrue("После входа доступно оформление заказа", main.isLoggedIn());
    }
}
