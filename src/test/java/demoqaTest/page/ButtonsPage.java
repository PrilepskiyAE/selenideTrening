package demoqaTest.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class ButtonsPage {
  private final SelenideElement btn = $x("//button[text()='Click Me']");
  private final SelenideElement rightBtn = $x("//button[@id='rightClickBtn']");
  private final SelenideElement doubleBtn = $x("//button[@id='doubleClickBtn']");
  private final SelenideElement text = $("#dynamicClickMessage");
  private final SelenideElement rightClickText = $x("//p[@id='rightClickMessage']");
  private final SelenideElement doubleClickText = $x("//p[@id='doubleClickMessage']");


   public SelenideElement btnClickAndDynamicText(){
       btn.click();
      return  text;
   }
    public SelenideElement btnRightClickAndDynamicText(){
        actions().contextClick(rightBtn).perform();
        return  rightClickText;
    }
    public SelenideElement btnDoubleClickAndDynamicText(){
        doubleBtn.doubleClick();
        return  doubleClickText;
    }
}
