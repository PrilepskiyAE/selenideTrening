package test.loggingtest;

import org.testng.Assert;
import org.testng.annotations.Test;
import test.base.BaseTest;

import static org.selenideTrenning.trening.constants.Constant.Urls.SAUCEDEMO_LOGIN_PAGE;

public class LoggingTest extends BaseTest
{
    @Test
    public void loginTest() {
        // Открываем страницу логина
        basePage.open(SAUCEDEMO_LOGIN_PAGE);

        // Выполняем логин
        saucedemoLoginPage.loggingIn();

        // Проверяем, открылся ли после логина  нужный URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory"), "Переход после логина не выполнен.");
    }
}
