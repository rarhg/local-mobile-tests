package tests;

import base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.AndroidOnboardingPage;

import static io.qameta.allure.Allure.step;

@Tag("android")
public class WikipediaOnboardingTest extends TestBase {

    private final AndroidOnboardingPage onboardingPage = new AndroidOnboardingPage();

    @Test
    @DisplayName("Успешное прохождение всех экранов Onboarding в Wikipedia")
    void testSuccessfulOnboarding() {

        step("Проверка первого экрана и переход далее", () -> {
            onboardingPage.checkFirstScreen()
                    .clickNext();
        });

        step("Проверка второго экрана и переход далее", () -> {
            onboardingPage.checkSecondScreen()
                    .clickNext();
        });

        step("Проверка третьего экрана и переход далее", () -> {
            onboardingPage.checkThirdScreen()
                    .clickNext();
        });

        step("Проверка четвертого экрана и переход далее", () -> {
            onboardingPage.checkFourthScreen()
                    .clickDone();
        });
    }
}