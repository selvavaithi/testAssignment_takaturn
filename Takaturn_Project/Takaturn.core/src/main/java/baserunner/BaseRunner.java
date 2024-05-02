package baserunner;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(

        glue = {"stepDefinition","base.parameterTransformers"},
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:failed/failed.txt",
                "timeline:test-output-thread/"},
        monochrome = true,
        publish = true
)
public abstract class BaseRunner {

    protected TestNGCucumberRunner testNGCucumberRunner;

    public static ThreadLocal<String> loggeruser = new ThreadLocal<String>();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        loggeruser.set(System.getProperty("user"));

    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
    }

}
