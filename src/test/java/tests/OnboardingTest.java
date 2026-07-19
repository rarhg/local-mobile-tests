package tests;

import base.TestBase;
import org.junit.jupiter.api.Tag;
import pages.AndroidOnboardingPage;
import org.junit.jupiter.api.Test;

@Tag("android")
public class OnboardingTest extends TestBase {

    @Test
    void shouldPassOnboarding() {
        AndroidOnboardingPage onboarding = new AndroidOnboardingPage();

        onboarding.checkFirstScreen()
                .clickNext()
                .checkSecondScreen()
                .clickNext()
                .checkThirdScreen()
                .clickNext()
                .checkFourthScreen()
                .clickDone();
    }
}