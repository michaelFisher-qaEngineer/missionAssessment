package tests.runners;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.listeners.RetryAnalyzer;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"tests"},
        tags = "@API",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber-pretty.html",
                "json:target/cucumber-reports/api/cucumber.json",
                "rerun:target/cucumber-reports/api/rerun.txt"
        },
        monochrome = true
)

public class TestAPIRunner extends AbstractTestNGCucumberTests {

}
