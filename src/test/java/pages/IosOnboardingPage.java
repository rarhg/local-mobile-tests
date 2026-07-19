package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class IosOnboardingPage {

    private final SelenideElement firstScreenIcon = $("");
    private final SelenideElement secondScreenIcon = $("");
    private final SelenideElement thirdScreenIcon = $("");
    private final SelenideElement fourthScreenIcon = $("");

    private final SelenideElement nextButton = $("");
    private final SelenideElement doneButton = $("");

    public IosOnboardingPage checkFirstScreen() {
        firstScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка второго экрана
    public IosOnboardingPage checkSecondScreen() {
        secondScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка третьего экрана
    public IosOnboardingPage checkThirdScreen() {
        thirdScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка четвёртого экрана
    public IosOnboardingPage checkFourthScreen() {
        fourthScreenIcon.shouldBe(visible);
        return this;
    }

    // Нажатие "Вперёд"
    public IosOnboardingPage clickNext() {
        nextButton.click();
        return this;
    }

    // Нажатие "Далее" на последнем экране
    public IosOnboardingPage clickDone() {
        doneButton.click();
        return this;
    }
}