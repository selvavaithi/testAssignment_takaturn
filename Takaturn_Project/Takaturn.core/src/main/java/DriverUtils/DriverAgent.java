package DriverUtils;

import Common.ConfigLoader;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverAgent {

    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
    private static Map<String, WebDriver> mapDrivers = new HashMap<String, WebDriver>();

    private static List<WebDriver> driverValut = new ArrayList<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                driverValut.forEach(WebDriver::quit);
            }
        });
    }

    public DriverAgent() {

    }

    public static WebDriver getDrivers() {
        //return drivers.get();
        return mapDrivers.get(Thread.currentThread().getName());
    }

    public static void disAbleImplicitWait(){
        getDrivers().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    public static void enableImplicitWait(){
        getDrivers().manage().timeouts().implicitlyWait(Duration.ofMillis(ConfigLoader.plsWaitTimeOut));
    }

    public static void addDriver(WebDriver dr) {
        driverValut.add(dr);
        drivers.set(dr);
        mapDrivers.put(Thread.currentThread().getName(), dr);
    }

    public static void releaseDriver() {
        WebDriver dr = drivers.get();
        driverValut.remove(drivers.get());
        System.out.println("CLOSE current Thread driver::: "+Thread.currentThread().getName());
        drivers.remove();
        dr.quit();
    }

}