package myPackage.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MyStepDefinition {
    private static final Logger log = LogManager.getLogger(MyStepDefinition.class);

    @Given("the game is setup")
    public void theGameIsSetup() {
        log.info("something");
    }

    @When("the Maker starts a game")
    public void theMakerStartsAGame() {
        log.info("something");

    }

    @Then("the Maker waits for a Breaker to join")
    public void theMakerWaitsForABreakerToJoin() {
        log.info("something");
    }

    @Given("url to search engine")
    public void urlToSearchEngine() {

    }

    @When("user search for {string} in search engine")
    public void userSearchForInSearchEngine(String arg0) {
        
    }

    @Then("results are shown")
    public void resultsAreShown() {

    }
}
