package test.pricesumtest;

import org.testng.Assert;
import org.testng.annotations.Test;
import test.base.BaseTest;

import static org.selenideTrenning.trening.constants.Constant.Urls.SAUCEDEMO_LOGIN_PAGE;

public class PriceSumTest extends BaseTest {

    @Test
    public void sumPriceTest() {
        // Открываем сайт и логинимся
        basePage.open(SAUCEDEMO_LOGIN_PAGE);
        saucedemoLoginPage.loggingIn();

        // Добавляем товары и переходим в корзину
        saucedemoShowcasePage.takeAndGoToCart();

        // Считаем итоговую сумму
        cartPage.calculateTotalPrice();

        // Проверяем сумму
        String expectedTotal = "$45,98"; // Цена двух товаров
        Assert.assertEquals(cartPage.getTotalPrice(), expectedTotal, "Сумма в корзине не совпадает.");
    }
}
