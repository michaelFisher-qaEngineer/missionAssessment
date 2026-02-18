package tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

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

public class TestRunnerALL extends AbstractTestNGCucumberTests {
	
}

