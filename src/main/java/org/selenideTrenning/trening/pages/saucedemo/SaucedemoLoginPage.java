package org.selenideTrenning.trening.pages.saucedemo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenideTrenning.trening.pages.base.BasePage;

public class SaucedemoLoginPage extends BasePage {

    public SaucedemoLoginPage(WebDriver driver) {
        super(driver);
    }

    // Локаторы — «указатели», позволяющие найти нужные элементы на странице
    private final By loginInput = By.xpath("//input[@placeholder='Username']");
    private final By passwordInput = By.xpath("//input[@placeholder='Password']");
    private final By loginButton = By.xpath("//input[@type='submit']");
    // Метод для авторизации: ввод логина и пароля + клик по кнопке
    public SaucedemoLoginPage loggingIn() {
        driver.findElement(loginInput).sendKeys("standard_user");
        driver.findElement(passwordInput).sendKeys("secret_sauce");
        driver.findElement(loginButton).click();
        return this;
    }

}
