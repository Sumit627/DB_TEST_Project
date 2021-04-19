package com.twitter.runnerfile;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(

        features = {"src/test/resources/"},
        glue = {"com/twitter/stepdefs"},
        plugin = {"html:target/cucumber-html-report",
                "json:target/cucumber.json"},
        dryRun = false, monochrome = true, snippets = CucumberOptions.SnippetType.CAMELCASE

)

public class RunnerTest extends AbstractTestNGCucumberTests {
	
}
