import eu.venthe.pipeline.orchestrator.Application;
import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("eu.venthe.pipeline.orchestrator")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/endToEndTest/resources/features")
@SpringBootTest(classes = Application.class)
@CucumberContextConfiguration
public class CucumberSpringGlue {
}
