package demoqaTest.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ElementsPage {
    //ul[@class='menu-list']//li[@id='item-4'] по такой маске находит 5 элиментов
    //более точечный поиск //div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-4']
    SelenideElement btn = $x("//div[@class='element-list accordion-collapse collapse show']//ul[@class='menu-list']//li[@id='item-4']//a[contains(@href, '/buttons')]");
    public ButtonsPage navigateButtonPage(){
        btn.click();
        return new  ButtonsPage();
    }
}
