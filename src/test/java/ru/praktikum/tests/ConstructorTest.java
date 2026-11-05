package ru.praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.pages.MainPage;
import ru.praktikum.support.UiTestBase;

import static io.qameta.allure.Allure.step;
import static org.junit.Assert.assertTrue;

public class ConstructorTest extends UiTestBase {
    public ConstructorTest(String browser) {
        super(browser);
    }

    @Test
    @DisplayName("Переход к разделу Булки из другого раздела")
    public void switchToBuns() {
        MainPage main = new MainPage(driver).open();
        step("Перейти к соусам, затем вернуться к булкам", () ->
                main.selectSection("Соусы").selectSection("Булки"));
        assertTrue("Активен раздел Булки", main.isSectionSelected("Булки"));
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    public void switchToSauces() {
        MainPage main = new MainPage(driver).open();
        step("Перейти к соусам", () -> main.selectSection("Соусы"));
        assertTrue("Активен раздел Соусы", main.isSectionSelected("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу Начинки")
    public void switchToFillings() {
        MainPage main = new MainPage(driver).open();
        step("Перейти к начинкам", () -> main.selectSection("Начинки"));
        assertTrue("Активен раздел Начинки", main.isSectionSelected("Начинки"));
    }
}
