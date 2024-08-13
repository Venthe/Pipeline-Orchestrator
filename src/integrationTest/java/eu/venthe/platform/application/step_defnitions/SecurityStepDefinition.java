package eu.venthe.platform.application.step_defnitions;

import eu.venthe.platform.application.Constants;
import eu.venthe.platform.application.fixtures.SecurityFixture;
import eu.venthe.platform.application.other.jwt.JsonWebToken;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;

@RequiredArgsConstructor
public class SecurityStepDefinition {
    private final SecurityFixture securityFixture;

    JsonWebToken token;

    @SneakyThrows
    @When("{string} logs in")
    public void logs_in(String username) {
        token = securityFixture.login(username, Constants.Keycloak.ADMINISTRATOR_PASSWORD);
    }

    @Then("login details are available")
    public void login_details_are_available() {
        Assertions.assertThat(token).isNotNull();
        securityFixture.verify(token);
    }
}
