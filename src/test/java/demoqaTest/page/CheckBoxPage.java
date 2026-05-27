package demoqaTest.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class CheckBoxPage {
    SelenideElement switcher = $(".rc-tree-switcher.rc-tree-switcher_close");
    SelenideElement checkbox = $("span.rc-tree-checkbox[role='checkbox'][aria-label='Select Downloads']");

    public SelenideElement findDownloadsAndCheck(){
        switcher.click();
        checkbox.click();
        return checkbox;
    }
}
