package demoqaTest.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    SelenideElement link = $x("//a[contains(@href, 'elements')]");
    public MainPage(String url){
        Selenide.open(url);
    }

    public ElementsPage navigateElementsPage(){
        link.click();
        return new  ElementsPage();
    }
}
