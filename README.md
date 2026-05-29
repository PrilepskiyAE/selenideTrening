# Selenide

**Selenide** — это Java-фреймворк для автоматизации тестирования веб‑интерфейсов, построенный поверх Selenium WebDriver. Его ключевая идея — сделать написание UI‑тестов проще, короче и надёжнее.

Selenide берёт на себя рутинные задачи: управление драйверами, ожидания, скриншоты при падениях. В результате код теста становится в 2–3 раза короче, чем при использовании «чистого» Selenium.

---

## Ключевые особенности и преимущества

- **Автоматические ожидания**: не нужно писать явные `wait` для каждого элемента. Selenide сам дождётся, пока элемент станет кликабельным или видимым.
- **Умный поиск элементов**: `$` (один элемент) и `$$` (коллекция элементов) работают стабильно даже на динамических страницах.
- **Встроенные отчёты и скриншоты**: при падении теста автоматически сохраняется скриншот и HTML‑страница.
- **Простота API**: команды звучат почти как естественный язык (`open()`, `click()`, `shouldHave(text("..."))`).
- **Лёгкая настройка**: минимум конфигурации «из коробки», поддержка Chrome, Firefox, Edge и др.
- **Интеграция с JUnit/TestNG**: легко встраивается в привычные фреймворки для запуска тестов.

---

## Настройка проекта

### Зависимости Maven
Добавьте в `pom.xml` зависимость Selenide и тестовый фреймворк (например, JUnit):

```xml
<dependency>
    <groupId>com.codeborne</groupId>
    <artifactId>selenide</artifactId>
    <version>7.0.5</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

### Базовый импорт
В тестах удобно использовать статический импорт, чтобы не писать длинные префиксы:

```java
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
```

---

## Основные методы и паттерны

### Открытие страницы и поиск элементов
```java
open("https://example.com");                    // открыть URL
$(By.id("login")).setValue("user");           // найти по ID и ввести текст
$("#submit").click();                          // найти по CSS-селектору и кликнуть
$$(".results li").shouldHave(size(5));        // проверить размер коллекции
```

### Условия и проверки (Assertions)
```java
$("#message").shouldBe(visible);               // элемент видим
$("#error").shouldNotBe(visible);             // элемент не видим
$("#title").shouldHave(text("Welcome"));     // содержит текст
$("#price").shouldHave(exactText("100 ₽"));  // точный текст
$("#email").shouldHave(value("test@test.com")); // значение поля ввода
```

### Ввод и действия
```java
$("#search").setValue("query");               // ввести текст
$("#search").pressEnter();                    // нажать Enter
$("#dropdown").selectOption("Option 1");     // выбрать из <select>
$("#file").uploadFile(new File("file.txt")); // загрузить файл
```

### Работа с окнами и фреймами
```java
switchTo().window(1);                          // переключиться на второе окно
switchTo().frame($("#myframe"));             // переключиться на iframe
```

### Специальные действия (Actions)
```java
actions().moveToElement($("#menu")).perform(); // наведение мыши
actions().dragAndDrop($("#drag"), $("#drop")).perform(); // drag-and-drop
```

---

## Пример простого теста

```java
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import org.junit.jupiter.api.Test;

class LoginTest {
    @Test
    void successfulLogin() {
        open("/login");
        $("#username").setValue("admin");
        $("#password").setValue("secret");
        $("#submit").click();
        $("#greeting").shouldHave(text("Hello, admin!"));
    }
}
```

---

## Продвинутые возможности

### Конфигурация Selenide (через класс Configuration)
Вы можете переопределить настройки по умолчанию:
```java
Configuration.browser = "chrome";
Configuration.timeout = 4000;        // таймаут ожидания в мс
Configuration.holdBrowserOpen = true; // не закрывать браузер после теста
Configuration.screenshots = true;      // делать скриншоты
Configuration.reportsFolder = "build/reports/ui"; // папка для отчётов
```

### Page Object Pattern
Для больших проектов удобно выносить элементы и методы в отдельные классы страниц:

```java
class LoginPage {
    private SelenideElement username = $("#username");
    private SelenideElement password = $("#password");
    private SelenideElement submit = $("#submit");

    public void login(String user, String pass) {
        username.setValue(user);
        password.setValue(pass);
        submit.click();
    }
}

// В тесте:
@Test
void loginTest() {
    LoginPage loginPage = new LoginPage();
    loginPage.login("admin", "secret");
    $("#greeting").shouldHave(text("Hello, admin!"));
}
```

### Data-Driven тесты
Можно параметризовать тесты, используя, например, JUnit 5 `@ParameterizedTest`:

```java
@ParameterizedTest
@CsvSource({
    "admin, secret, Hello, admin!",
    "user, test, Welcome"
})
void loginWithParams(String user, String pass, String expectedText) {
    open("/login");
    $("#username").setValue(user);
    $("#password").setValue(pass);
    $("#submit").click();
    $("#greeting").shouldHave(text(expectedText));
}
```

### Тестирование динамических элементов
Selenide автоматически ждёт появления элементов, но можно задать кастомные условия:

```java
$("#spinner").shouldNotBe(visible, Duration.ofSeconds(10)); // ждать исчезновения
$("#result").shouldAppear();                               // ждать появления
```

---

## Интеграция и окружение

- **CI/CD**: тесты Selenide легко запускаются в Jenkins, GitLab CI, GitHub Actions.
- **Selenium Grid / Selenoid**: можно настроить удалённый запуск в Grid, указав `Configuration.remote`.
- **Docker**: удобно запускать тесты в контейнере с браузером (например, через Selenoid).
- **Allure Reports**: интеграция с Allure для красивых отчётов.

---

## Отладка и диагностика

- **Логи**: Selenide пишет логи в консоль (можно перенаправить в файл).
- **Скриншоты**: автоматически сохраняются в `build/reports/tests` при падении.
- **HTML-снимок страницы**: тоже сохраняется вместе со скриншотом.
- **Видео**: при использовании Selenoid можно записывать видео сессии.

---

## Типичные ошибки новичков

1. **Смешивание явных и неявных ожиданий**: Selenide уже ждёт элементы, не нужно дублировать `WebDriverWait`.
2. **Слишком сложные селекторы**: старайтесь использовать простые CSS/ID, а не громоздкие XPath.
3. **Игнорирование Page Object**: в больших проектах без него быстро наступает хаос.
4. **Неправильные таймауты**: если тест падает на ожидании, проверьте `Configuration.timeout`.

---

## Сравнение с Selenium

| Задача | Selenium | Selenide |
|---|---|---|
| Открыть страницу | `driver.get(url)` | `open(url)` |
| Найти элемент и кликнуть | `driver.findElement(By.id("btn")).click()` | `$("#btn").click()` |
| Ввести текст и нажать Enter | `el.sendKeys("text"); el.sendKeys(Keys.ENTER);` | `$(...).setValue("text").pressEnter()` |
| Проверить текст элемента | `assertEquals(el.getText(), "Hello")` | `$(...).shouldHave(text("Hello"))` |
| Ожидание видимости элемента | `wait.until(ExpectedConditions.visibilityOf(el))` | `$(...).shouldBe(visible)` |

Как видите, Selenide сокращает объём кода и делает его более читаемым.

---
