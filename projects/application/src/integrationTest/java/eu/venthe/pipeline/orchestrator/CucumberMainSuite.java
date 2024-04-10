package eu.venthe.pipeline.orchestrator;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "eu.venthe.pipeline.orchestrator")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "features")
public class CucumberMainSuite {
}
