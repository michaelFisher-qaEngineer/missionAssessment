package mission;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission", "stepDefs", "hooks"},
        tags = "@API",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber-pretty.html",
                "json:target/cucumber-reports/api/cucumber.json",
                "rerun:target/cucumber-reports/api/rerun.txt"
        },
        monochrome = true
)

public class TestRunnerAPI extends AbstractTestNGCucumberTests {
	
}
