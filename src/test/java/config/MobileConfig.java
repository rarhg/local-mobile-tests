package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:${deviceHost}.properties")
public interface MobileConfig extends Config {

    @Key("platform.name")
    String platformName();

    @Key("automation.name")
    String automationName();

    @Key("device.name")
    String deviceName();

    @Key("platform.version")
    String platformVersion();

    @Key("app.package")
    String appPackage();

    @Key("app.activity")
    String appActivity();

    @Key("app.path")
    String appPath();

    @Key("appium.url")
    String appiumUrl();
}