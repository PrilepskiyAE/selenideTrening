package org.selenideTrenning.trening.basic;

public class Config {
    public static final String OS_AND_BROWSER = "linux-chrome";
    public static final Boolean CLEAR_COOKIES_AND_STORAGE = true;

    // Если false — браузер будет закрываться после завершения теста
    // Если true — браузер останется открытым (например, для отладки)
    public static final Boolean HOLD_BROWSER_OPEN = false;
}
