package eu.venthe.pipeline.application.step_defnitions;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class IsItFridayStepDefinitions {
    private final KeycloakContainer keycloakContainer;

    String today;
    String msg;

    @Given("today is Sunday")
    public void todayIsSunday() {
        today = "sunday";
    }

    @When("I ask whether it's Friday yet")
    public void iAskWhetherItSFridayYet() {
        if (Objects.equals(today, "friday")) {
            msg = "Yep";
        } else {
            msg = "Nope";
        }
    }

    @Then("I should be told {string}")
    public void iShouldBeTold(String arg0) {
        Assertions.assertThat(msg).isEqualToIgnoringCase(arg0);
    }
}
