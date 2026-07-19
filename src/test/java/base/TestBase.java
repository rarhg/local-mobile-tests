package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    protected AppiumDriver driver;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        WebDriverRunner.setWebDriver(driver);

        Configuration.browserSize = null; // важно для мобилок
        Configuration.timeout = 15000;    // 15 секунд на ожидание

        // Allure-логирование
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}