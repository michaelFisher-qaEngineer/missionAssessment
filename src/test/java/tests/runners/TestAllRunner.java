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
		tags = "(@UI or @API) and not @wip",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/all/cucumber-pretty.html",
                "json:target/cucumber-reports/all/cucumber.json",
                "rerun:target/cucumber-reports/all/rerun.txt"
        },
        monochrome = true
)

public class TestAllRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
    	return super.scenarios();
	}
    
    @Test(dataProvider = "scenarios", retryAnalyzer = RetryAnalyzer.class)
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }


}

