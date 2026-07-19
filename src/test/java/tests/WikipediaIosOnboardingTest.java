package tests;

import base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.IosOnboardingPage;

import static io.qameta.allure.Allure.step;

@Tag("ios") // Этот тег связывает тест с таской 'task ios' в build.gradle
public class WikipediaIosOnboardingTest extends TestBase {

    private final IosOnboardingPage iosOnboardingPage = new IosOnboardingPage();

    @Test
    @DisplayName("Успешное прохождение всех экранов Onboarding в Wikipedia на iOS")
    void testSuccessfulIosOnboarding() {

        step("Проверка первого экрана iOS и переход далее", () -> {
            iosOnboardingPage.checkFirstScreen()
                    .clickNext();
        });

        step("Проверка второго экрана iOS и переход далее", () -> {
            iosOnboardingPage.checkSecondScreen()
                    .clickNext();
        });

        step("Проверка третьего экрана iOS и переход далее", () -> {
            iosOnboardingPage.checkThirdScreen()
                    .clickNext();
        });

        step("Проверка четвёртого экрана iOS и завершение приветствия", () -> {
            iosOnboardingPage.checkFourthScreen()
                    .clickDone();
        });
    }
}
