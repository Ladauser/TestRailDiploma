package tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = {"classpath:features"},
        glue = {"steps", "webdriver"},
        tags = "@SmokeTest",
        plugin = {"pretty", "html:target/cucumber.html",
                "json:target/cucumber-report/cucumber.json"}
)

public class TestRailTest extends AbstractTestNGCucumberTests {

}
