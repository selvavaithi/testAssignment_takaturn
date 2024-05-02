package stepDefinition;

import ComponentController.TakaturnComponents;
import DriverUtils.SharedDriver;
import base.TestContext;
import base.transformers.CustomString;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static Components.IComponents.getComponents;

public class GivenTakaturnLoginStepDefinition {

    private TestContext testContext;

    public GivenTakaturnLoginStepDefinition(SharedDriver dr,
                                         TestContext testContext) {
        this.testContext=testContext;
    }

    @Given("User visit {CustomStringTransform} url page")
    public void userUrlpage(CustomString item) {
        item=testContext.updateFromCustomString(item);
        getComponents(TakaturnComponents::new).gotoUrl(item);

    }

    @Given("User visit Takaturn Home page")
    public void userUrlpage() {
        getComponents(TakaturnComponents::new).gotoHomePage();

    }

    @When("User login Takaturn using valid credentials")
    public void user_login_bni_using_valid_credentials() {
        getComponents(TakaturnComponents::new).userEntersCredentials().userHitLoginPObj();
    }


    @Then("User verify that he is logged in")
    public void userVerifyThatHeIsLoggedIn() {
        getComponents(TakaturnComponents::new).verifyProfileIcon();

    }

    @And("User clicks on {string} button")
    public void userClicksOnButton(String btn) {
        getComponents(TakaturnComponents::new).userClickText(btn);
    }
}
