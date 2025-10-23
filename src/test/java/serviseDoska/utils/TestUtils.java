package serviseDoska.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

// Утилиты для настройки тестов
public class TestUtils {

    public static void setUp() {
        // Базовая конфигурация
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;

        // Настройки для Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Создаём драйвер
        RemoteWebDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);

        // Гарантированное максимизирование
        driver.manage().window().maximize();
    }

    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
