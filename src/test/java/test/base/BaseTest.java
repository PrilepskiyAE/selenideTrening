package test.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.selenideTrenning.trening.basic.BasicActions;
import org.selenideTrenning.trening.cart.CartPage;
import org.selenideTrenning.trening.pages.base.BasePage;
import org.selenideTrenning.trening.pages.saucedemo.SaucedemoLoginPage;
import org.selenideTrenning.trening.pages.showcase.SaucedemoShowcasePage;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

import static org.selenideTrenning.trening.basic.Config.CLEAR_COOKIES_AND_STORAGE;
import static org.selenideTrenning.trening.basic.Config.HOLD_BROWSER_OPEN;

public class BaseTest {
    protected WebDriver driver = BasicActions.createDriver();
    protected BasePage basePage = new BasePage(driver);
    protected SaucedemoLoginPage saucedemoLoginPage = new SaucedemoLoginPage(driver);
    protected SaucedemoShowcasePage saucedemoShowcasePage = new SaucedemoShowcasePage(driver);
    protected CartPage cartPage = new CartPage(driver);

    @AfterTest
    public void clearCookiesAndLocalStorage() {
        if (CLEAR_COOKIES_AND_STORAGE) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            driver.manage().deleteAllCookies();
            js.executeScript("window.sessionStorage.clear()");
        }
    }

    // После всех тестов — закрытие браузера
    @AfterSuite
    public void clear() {
        if (!HOLD_BROWSER_OPEN) {
            driver.quit();
        }
    }
}
