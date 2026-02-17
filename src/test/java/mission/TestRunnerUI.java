package mission;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission", "stepDefs", "hooks"},
        tags = "@UI",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui/cucumber-pretty.html",
                "json:target/cucumber-reports/ui/cucumber.json",
                "rerun:target/cucumber-reports/ui/rerun.txt"
        },
        monochrome = true
)
public class TestRunnerUI extends AbstractTestNGCucumberTests {
	
}
