package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AndroidOnboardingPage {

    private final SelenideElement firstScreenIcon = $("[content-desc='Википедия']");
    private final SelenideElement secondScreenIcon = $("[content-desc='Данные и конфиденциальность']");
    private final SelenideElement thirdScreenIcon = $("[content-desc='Читайте на более чем 300 языках']");
    private final SelenideElement fourthScreenIcon = $("[content-desc='Следуйте за своим любопытством']");

    private final SelenideElement nextButton = $("[content-desc='Вперёд']");
    private final SelenideElement doneButton = $("[content-desc='Далее']");

    // Проверка первого экрана
    public AndroidOnboardingPage checkFirstScreen() {
        firstScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка второго экрана
    public AndroidOnboardingPage checkSecondScreen() {
        secondScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка третьего экрана
    public AndroidOnboardingPage checkThirdScreen() {
        thirdScreenIcon.shouldBe(visible);
        return this;
    }

    // Проверка четвёртого экрана
    public AndroidOnboardingPage checkFourthScreen() {
        fourthScreenIcon.shouldBe(visible);
        return this;
    }

    // Нажатие "Вперёд"
    public AndroidOnboardingPage clickNext() {
        nextButton.click();
        return this;
    }

    // Нажатие "Далее" на последнем экране
    public AndroidOnboardingPage clickDone() {
        doneButton.click();
        return this;
    }
}