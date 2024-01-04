package com.testowanieoprogramowaniaprojekt2;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/testowanieoprogramowaniaprojekt2")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value="pretty, html:target/cucumber-reports/Cucumber.html")
public class RunCucumberTest {
}
