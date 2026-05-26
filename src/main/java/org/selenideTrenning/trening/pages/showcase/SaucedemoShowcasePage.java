package org.selenideTrenning.trening.pages.showcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenideTrenning.trening.pages.base.BasePage;

public class SaucedemoShowcasePage extends BasePage {
    public SaucedemoShowcasePage(WebDriver driver) {
        super(driver);
    }

    private final By backpackButton = By.xpath("//button[@name='add-to-cart-sauce-labs-backpack']");
    private final By blackTshirtButton = By.xpath("//button[@name='add-to-cart-sauce-labs-bolt-t-shirt']");
    private final By cartLinkButton = By.xpath("//a[@class='shopping_cart_link']");

    public SaucedemoShowcasePage takeAndGoToCart() {
        driver.findElement(backpackButton).click();
        driver.findElement(blackTshirtButton).click();
        driver.findElement(cartLinkButton).click();
        return this;
    }
}
