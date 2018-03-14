package uk.gov.digital.ho.hocs.executableSpecification;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = "pretty", features = "src/test/resources/features", tags={"~@Ignore"})
public class AllFeatureTest {
}