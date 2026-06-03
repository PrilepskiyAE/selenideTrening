package demoqaTest;

import base.BaseTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import demoqaTest.page.CheckBoxPage;
import demoqaTest.page.MainPage;
import demoqaTest.page.RadioButtonPage;
import demoqaTest.page.WebTablesPage;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class DemoQaTest extends BaseTest {
    private final static String BASE_URL = "https://demoqa.com/";
    private final static String MESSAGE_DYNAMIC="You have done a dynamic click";
    private final static String MESSAGE_RIGHT="You have done a right click";
    private final static String MESSAGE_DOUBLE="You have done a double click";
    private final static String BUTTONS_URL="https://demoqa.com/buttons";
    private final static String MESSAGE_YES="Yes";
    private final static String MESSAGE_IMPRESSIVE="Impressive";
    private final static String FIRST_NAME="first-name";
    private final static String LAST_NAME="last-name";
    private final static String EMAIL="test@test.ru";
    private final static String AGE="66";
    private final static String SALARY="2000";
    private final static String DEP="Testing";



    private final static int ROWS_SIZE = 3;
    private final static int HEADERS_SIZE = 7;


    @Test
   public void clickButtonTest(){
        new MainPage(BASE_URL).navigateElementsPage().navigateButtonPage().btnClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_DYNAMIC));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }
    @Test
    public void rightClickButtonTest(){
        new MainPage(BASE_URL).navigateElementsPage().navigateButtonPage().btnRightClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_RIGHT));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }
    @Test
    public void doubleClickButtonTest(){
        new MainPage(BASE_URL).navigateElementsPage().navigateButtonPage().btnDoubleClickAndDynamicText().shouldBe(Condition.visible)
                .shouldHave(Condition.exactText(MESSAGE_DOUBLE));
        assertEquals(BUTTONS_URL, WebDriverRunner.url());
    }

    @Test
    public void radioButtonTest(){
        RadioButtonPage page = new MainPage(BASE_URL).navigateElementsPage().navigateRadioButtonPage();
        SelenideElement text = page.getText();
        text.shouldNotBe(Condition.visible);
        SelenideElement rBtnYesClick = page.rBtnYesClick();
        rBtnYesClick.shouldBe(Condition.selected);
        text.shouldBe(Condition.visible).shouldHave(Condition.exactText(MESSAGE_YES));
        SelenideElement rBtnImpClick = page.rBtnImpressiveClick();
        rBtnYesClick.shouldNotBe(Condition.selected);
        rBtnImpClick.shouldBe(Condition.selected);
        text.shouldBe(Condition.visible).shouldHave(Condition.exactText(MESSAGE_IMPRESSIVE));
    }

    @Test
    public void checkButtonTest(){
        CheckBoxPage page = new MainPage(BASE_URL).navigateElementsPage().navigateCheckBoxPage();
        SelenideElement checkbox = page.findDownloadsAndCheck();
        checkbox.shouldHave(Condition.attribute("aria-checked", "true"));
    }

    @Test
    public void webTablesTest(){

        final List<String> tabHeaders = List.of(
                "First Name",
                "Last Name",
                "Age",
                "Email",
                "Salary",
                "Department",
                "Action"
        );

        WebTablesPage page = new MainPage(BASE_URL).navigateElementsPage().navigateWebTablesPage();

        page.getTable().shouldBe(Condition.visible);

        var headers = page.getHeaders();
        assertEquals(HEADERS_SIZE, headers.size());

        assertEquals(tabHeaders.get(0), headers.get(0).getText());
        assertEquals(tabHeaders.get(1), headers.get(1).getText());
        assertEquals(tabHeaders.get(2), headers.get(2).getText());
        assertEquals(tabHeaders.get(3), headers.get(3).getText());
        assertEquals(tabHeaders.get(4), headers.get(4).getText());
        assertEquals(tabHeaders.get(5), headers.get(5).getText());
        assertEquals(tabHeaders.get(6), headers.get(6).getText());

        var rows = page.getTableRows();
        assertEquals(ROWS_SIZE, rows.size());
        page.clickAddButton().shouldBe(Condition.visible);
        page.addItem(FIRST_NAME,LAST_NAME,EMAIL,AGE,SALARY,DEP);
        assertEquals(ROWS_SIZE+1, rows.size());
        var firstRowCells = rows.last().$$("td");
        assertEquals(FIRST_NAME, firstRowCells.first().getText());
        assertEquals(LAST_NAME, firstRowCells.get(1).getText());
        assertEquals(AGE, firstRowCells.get(2).getText());
        assertEquals(EMAIL, firstRowCells.get(3).getText());
        assertEquals(SALARY, firstRowCells.get(4).getText());
        assertEquals(DEP, firstRowCells.get(5).getText());
    }

}
