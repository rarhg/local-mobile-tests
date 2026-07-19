package driver;

import config.MobileConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumClientConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.aeonbits.owner.ConfigFactory;

import java.net.URI;
import java.util.Map;

public class DriverFactory {

    private static final MobileConfig CONFIG = ConfigFactory.create(MobileConfig.class);

    public static AppiumDriver createDriver() {
        String urlString = CONFIG.appiumUrl();
        String appPath = CONFIG.appPath();

        // Защита: Если мы в Jenkins, и нам передали логин/пароль через системные переменные,
        // мы можем динамически собрать URL для BrowserStack в целях безопасности!
        String envUser = System.getenv("BS_USER");
        String envKey = System.getenv("BS_KEY");

        if (urlString.contains("browserstack.com") && envUser != null && envKey != null) {
            // Подставляем секретные ключи из Jenkins прямо в URL «на лету», не храня их в Git
            urlString = "https://" + envUser + ":" + envKey + "@://browserstack.com";
        }

        URI appiumUri = URI.create(urlString);
        AppiumClientConfig clientConfig = AppiumClientConfig.defaultConfig().baseUri(appiumUri);

        if (CONFIG.platformName().equalsIgnoreCase("Android")) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setNoReset(false);
            options.setDeviceName(CONFIG.deviceName());
            options.setPlatformVersion(CONFIG.platformVersion());
            options.setAutomationName(CONFIG.automationName());
            options.setAppPackage(CONFIG.appPackage());
            options.setAppActivity(CONFIG.appActivity());

            if (CONFIG.appPath() != null && !CONFIG.appPath().isEmpty()) {
                options.setApp(CONFIG.appPath());
            }

            // Настройка под BrowserStack, если выбран этот стенд
            if (urlString.contains("browserstack.com")) {
                options.setCapability("bstack:options", Map.of(
                        "projectName", "Wikipedia Automation",
                        "buildName", "Onboarding-Build",
                        "sessionName", "Wikipedia Onboarding Test Run"
                ));
            }

            return new AndroidDriver(clientConfig, options);

        } else if (CONFIG.platformName().equalsIgnoreCase("iOS")) {
            XCUITestOptions options = new XCUITestOptions();
            options.setDeviceName(CONFIG.deviceName());
            options.setPlatformVersion(CONFIG.platformVersion());
            options.setAutomationName(CONFIG.automationName());

            if (CONFIG.appPath() != null && !CONFIG.appPath().isEmpty()) {
                options.setApp(CONFIG.appPath());
            }

            return new IOSDriver(clientConfig, options);

        } else {
            throw new IllegalArgumentException("Unsupported platform: " + CONFIG.platformName());
        }
    }


}