package demoqaTest.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class RadioButtonPage {
    private final SelenideElement rBtnYes = $x("//input[@id='yesRadio']");
    private final SelenideElement rBtnImpressive = $x("//input[@id='impressiveRadio']");
    private final SelenideElement rBtnNo = $x("//input[@id='noRadio']");
    private final SelenideElement text = $x("//p[@class='mt-3' and text()='You have selected ']//span[@class='text-success']");

    public SelenideElement rBtnYesClick(){
        rBtnYes.click();
        return rBtnYes;
    }
    public SelenideElement rBtnNoClick(){
        rBtnNo.click();
        return rBtnNo;
    }

    public SelenideElement rBtnImpressiveClick(){
        rBtnImpressive.click();
        return rBtnImpressive;
    }
    public SelenideElement getText(){
        return text;
    }

}
