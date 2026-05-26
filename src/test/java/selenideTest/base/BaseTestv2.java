package selenideTest.base;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.Before;
import org.junit.After;


abstract public class BaseTestv2 {
    public void setUp(){
        //WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
      //  Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.browserVersion = "148.0";
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
