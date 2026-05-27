package demoqaTest.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ElementsPage {
    //ul[@class='menu-list']//li[@id='item-4'] по такой маске находит 5 элиментов
    //более точечный поиск //div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-4']
    private final SelenideElement btnButtons = $x("//div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-4']//a[contains(@href, '/buttons')]");
    private final SelenideElement btnRadioButtons = $x("//div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-2']//a[contains(@href, '/radio-button')]");
    private final SelenideElement btnCheckBox = $x("//div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-1']//a[contains(@href, '/checkbox')]");
    private final SelenideElement btnWebTables = $x("//div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-3']//a[contains(@href, '/webtables')]");

    public ButtonsPage navigateButtonPage(){
        btnButtons.click();
        return new  ButtonsPage();
    }

    public RadioButtonPage navigateRadioButtonPage(){
        btnRadioButtons.click();
        return new RadioButtonPage();
    }

    public CheckBoxPage navigateCheckBoxPage(){
        btnCheckBox.click();
        return new CheckBoxPage();
    }

    public WebTablesPage navigateWebTablesPage(){
        btnWebTables.click();
        return new WebTablesPage();
    }
}
