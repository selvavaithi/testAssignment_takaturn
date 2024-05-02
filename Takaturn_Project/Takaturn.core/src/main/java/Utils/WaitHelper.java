package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static DriverUtils.DriverAgent.getDrivers;

public class WaitHelper {

    public static WebElement waitForElement(String str) {
        WebDriverWait wait = new WebDriverWait(getDrivers(), Duration.ofSeconds(30));

        if (str.startsWith("//") || str.startsWith("./"))
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(str)));
        else
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(str)));
    }
}
