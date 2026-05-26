package org.selenideTrenning.trening.cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.selenideTrenning.trening.pages.base.BasePage;

// Страница корзины. Получаем цены двух товаров и считаем сумму
public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Локатор цены рюкзака
    private final By backpackInCart = By.xpath(
            "//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']//..//..//div[@class='inventory_item_price']"
    );

    // Локатор цены футболки
    private final By blackTshirtInCart = By.xpath(
            "//div[@class='inventory_item_name' and text()='Sauce Labs Bolt T-Shirt']//..//..//div[@class='inventory_item_price']"
    );

    private String totalPrice;

    // Метод рассчитывает сумму товаров и сохраняет в переменную
    public CartPage calculateTotalPrice() {
        // Получаем элементы с ценами
        WebElement backpackPriceElement = driver.findElement(backpackInCart);
        WebElement blackTshirtPriceElement = driver.findElement(blackTshirtInCart);

        // Преобразуем текст из "$29.99" в число
        double backpackPrice = Double.parseDouble(backpackPriceElement.getText().replace("$", ""));
        double blackTshirtPrice = Double.parseDouble(blackTshirtPriceElement.getText().replace("$", ""));

        // Складываем и форматируем в строку с долларом и двумя знаками после запятой
        double total = backpackPrice + blackTshirtPrice;
        this.totalPrice = String.format("$%.2f", total);

        return this;
    }

    // Метод возвращает ранее рассчитанную сумму
    public String getTotalPrice() {
        return totalPrice;
    }
}
