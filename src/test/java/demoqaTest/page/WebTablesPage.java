package demoqaTest.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class WebTablesPage {

    private final SelenideElement table = $("table.table.table-striped.table-bordered.table-hover");
    private final ElementsCollection headers = table.$$("thead th");
    private final ElementsCollection tableRows = table.$$("tbody tr");
    private final SelenideElement addButton = $x("//button[@id='addNewRecordButton']");

    private final SelenideElement firstName  = $x("//input[@id='firstName']");
    private final SelenideElement lastName = $x("//input[@id='lastName']");
    private final SelenideElement email = $x("//input[@id='userEmail']");
    private final SelenideElement age = $x("//input[@id='age']");
    private final SelenideElement salary = $x("//input[@id='salary']");
    private final SelenideElement department = $x("//input[@id='department']");
    private final SelenideElement submit =  $x("//button[@id='submit']");

    public ElementsCollection getHeaders(){
        return headers;
    }

    public ElementsCollection getTableRows() {
        return tableRows;
    }

    public SelenideElement clickAddButton(){
        addButton.click();
        return addButton;
    }

    public SelenideElement getTable() {
        return table;
    }

    public SelenideElement getAddButton() {
        return addButton;
    }

    public SelenideElement getFirstName() {
        return firstName;
    }

    public SelenideElement getLastName() {
        return lastName;
    }

    public SelenideElement getEmail() {
        return email;
    }

    public SelenideElement getAge() {
        return age;
    }

    public SelenideElement getSalary() {
        return salary;
    }

    public SelenideElement getDepartment() {
        return department;
    }

    public SelenideElement getSubmit() {
        return submit;
    }

    public void addItem(String firstName,String lastName,String email,String age,String salary,String department){
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.email.setValue(email);
        this.age.setValue(age);
        this.salary.setValue(salary);
        this.department.setValue(department);
        submit.click();
    }

}
