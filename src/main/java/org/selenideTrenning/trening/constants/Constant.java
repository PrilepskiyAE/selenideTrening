package org.selenideTrenning.trening.constants;


import java.time.Duration;

public class Constant {
    // Переменные, связанные с таймингами ожидания
    public static class TimeoutVariable {
        // Время неявного ожидания (в секундах) — Selenium будет ждать элементы на странице
        public static final int IMPLICIT_WAIT = 5;

        // Время явного ожидания — можно использовать в WebDriverWait
        public static final Duration EXPLICIT_WAIT = Duration.ofSeconds(10);
    }

    // URL-адреса страниц, с которыми работают тесты
    public static class Urls {
        // Страница логина сайта, который мы тестируем
        public static final String SAUCEDEMO_LOGIN_PAGE = "https://www.saucedemo.com/";
    }
}