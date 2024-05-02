package ComponentController;

import Common.ConfigLoader;
import Components.BaseComponents;
import DriverUtils.DriverAgent;
import base.transformers.CustomString;
import com.typesafe.config.Config;
import org.testng.Assert;

import static Utils.BaseUtils.*;
import static extensions.IReporter.addStepLog;

public class TakaturnComponents extends BaseComponents {

    private Config xp = ConfigLoader.config;
// input#email
    public TakaturnComponents userEntersCredentials() {
        waitForPageToLoad();
        addStepLog(String.format("Username: %s, Password: %s",System.getProperty("user"), System.getProperty("pass")));
        findEl(xp.getString("tkturn_emailId")).clear();
        findEl(xp.getString("tkturn_emailId")).sendKeys(System.getProperty("user"));
        findEl(xp.getString("tkturn_pwd")).clear();
        findEl(xp.getString("tkturn_pwd")).sendKeys(System.getProperty("pass"));

        return this;
    }

    public void userHitLoginPObj(){
        findEl(xp.getString("loginBtn")).click();
    }

    public TakaturnComponents userLogoutApp(){

        waitForPageToLoad();
        addStepLog(String.format("Username: %s, Password: %s",System.getProperty("user"), System.getProperty("pass")));
        findEl(xp.getString("profile_icon")).click();

        return this;
    }

    public TakaturnComponents userClickText(String btn){

        waitForPageToLoad();
        findEl(String.format("//a[text()='%s']",btn)).click();
        return this;
    }

    public void gotoUrl(CustomString url) {
        DriverAgent.getDrivers().get(url.getValue());
        waitForPageToLoad();
        addStepLog(String.format("GoTo URL: %s",url.getValue()));
    }

    public void gotoHomePage() {
        DriverAgent.getDrivers().get(xp.getString("appurl"));
    }

    public void verifyProfileIcon(){
        waitForPageToLoad();
        Assert.assertTrue(findEls(xp.getString("profile_icon")).size()>0);
    }

}
