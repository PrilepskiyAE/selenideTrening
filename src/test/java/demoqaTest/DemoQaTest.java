package demoqaTest;

import base.BaseTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import demoqaTest.page.MainPage;
import org.junit.Test;


import static org.testng.AssertJUnit.assertEquals;

public class DemoQaTest extends BaseTest {
    private final static String BASE_URL = "https://demoqa.com/";
    private final static String MESSAGE_DYNAMIC="You have done a dynamic click";
    private final static String MESSAGE_RIGHT="You have done a right click";
    private final static String MESSAGE_DOUBLE="You have done a double click";
    private final static String BUTTONS_URL="https://demoqa.com/buttons";

    @Test
   public void clickButtonTest(){
        MainPage test = new MainPage(BASE_URL);
        test.navigateElementsPage().navigateButtonPage().btnClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_DYNAMIC));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }
    @Test
    public void rightClickButtonTest(){
        MainPage test = new MainPage(BASE_URL);
        test.navigateElementsPage().navigateButtonPage().btnRightClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_RIGHT));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }
    @Test
    public void doubleClickButtonTest(){
        MainPage test = new MainPage(BASE_URL);
        test.navigateElementsPage().navigateButtonPage().btnDoubleClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_DOUBLE));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }

}
