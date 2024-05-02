package DriverUtils;

import Common.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SharedDriver {

    public SharedDriver(){

        String browser = System.getProperty("browser");
        String apitest =System.getProperty("apitest","false");

        browser=(apitest.equalsIgnoreCase("false")?browser:apitest);

        switch(browser.toLowerCase()){
            case "chrome":{
                if(DriverAgent.getDrivers()==null){
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options=new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    WebDriver driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigLoader.plsWaitTimeOut));
                    driver.manage().window().maximize();

                    DriverAgent.addDriver(driver);
                    break;
                }
            }

            case "headlessChrome":{
                if(DriverAgent.getDrivers()==null){
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options=new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--headless=new");
                    WebDriver driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigLoader.plsWaitTimeOut));
                    driver.manage().window().maximize();

                    DriverAgent.addDriver(driver);
                    break;
                }
            }
            //case "remote-chrome":{
            case "chrome1":{
                if(DriverAgent.getDrivers()==null){
                    //WebDriverManager.chromedriver().setup();
                    DesiredCapabilities desire = new DesiredCapabilities();
                    desire.setBrowserName("chrome");
                    WebDriver driver = null;
                    try {
                        ChromeOptions options=new ChromeOptions();
                        options.addArguments("--remote-allow-origins=*");
                        options.setCapability("platformName", "Windows");
// Showing a test name instead of the session id in the Grid UI
                        options.setCapability("se:name", "My simple test");
// Other type of metadata can be seen in the Grid UI by clicking on the
// session info or via GraphQL
                        options.setCapability("se:sampleMetadata", "Sample metadata value");
                        driver = new RemoteWebDriver(new URL("http://192.168.1.175:4444/"), options);
                        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigLoader.plsWaitTimeOut));
                        driver.manage().window().maximize();

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                    DriverAgent.addDriver(driver);
                    break;
                }
            }
            case "edge":{
                if(DriverAgent.getDrivers()==null){
                    WebDriverManager.edgedriver().setup();
                    WebDriver driver = new EdgeDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigLoader.plsWaitTimeOut));
                    driver.manage().window().maximize();

                    DriverAgent.addDriver(driver);
                    break;
                }
            }
            default:{
                break;
            }
        }

    }
}
