package mission;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"mission"},
        // tags = "@smoke and not @wip",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },
        monochrome = true
)
public class RunnerTest extends AbstractTestNGCucumberTests {
    // nothing else needed
	
}
