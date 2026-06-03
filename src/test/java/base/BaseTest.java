package base;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import org.junit.Before;
import org.junit.After;


abstract public class BaseTest {
    @Before
    public void setUp() {
        // URL Selenoid Hub (берётся из system property или используется значение по умолчанию)
        String selenoidUrl = System.getProperty("selenoid.url", "http://localhost:4444/wd/hub");
        Configuration.remote = selenoidUrl;

        // Настройки браузера
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true; // Для CI/CD обычно headless

        // Дополнительные настройки Selenoid через capabilities
        Configuration.browserCapabilities.setCapability("enableVNC", true);
        Configuration.browserCapabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities.setCapability("name", "Selenide Test");

        // Таймауты
        Configuration.timeout = 10000; // 10 секунд
        Configuration.pageLoadTimeout = 30000; // 30 секунд
    }


    @Before
    public void init(){
        setUp();
    }
    @After
    public void tearDown(){

        Selenide.closeWebDriver();
    }
}
