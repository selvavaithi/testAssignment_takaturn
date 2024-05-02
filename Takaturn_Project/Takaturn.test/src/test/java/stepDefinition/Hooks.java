package stepDefinition;

import base.TestContext;
import base.transformers.DataMedium;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.Date;

import static DriverUtils.DriverAgent.getDrivers;
import static DriverUtils.DriverAgent.releaseDriver;
import static extensions.IReporter.addStepLog;

public class Hooks {

    TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @AfterStep
    public void afterStepValue(Scenario scenario) {

        if (scenario.isFailed()) {
            String sr = String.format("\t\t<span style=\"color:blue;font-weight:bold\">%s</span><br/>", "TIME::" + new Date());
           // ExtentCucumberAdapter.getCurrentStep().info(sr);
            addStepLog(sr);

        }
    }

    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {

        DataMedium.setTestContext(testContext);

        if (scenario.isFailed() && !System.getProperty("apitest").equalsIgnoreCase("true")) {

            TakesScreenshot ts = (TakesScreenshot) getDrivers();
            try {
                byte[] src = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(src, "image/png", "fail_" + System.currentTimeMillis());
            }catch (Exception e){
//                e.printStackTrace();
                System.out.println("ScreenShot Exception: "+e.getMessage());
            }
        }
        if (getDrivers() != null) {
            releaseDriver();
        }
    }
}
