package Utils;

import Common.ConfigLoader;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static DriverUtils.DriverAgent.*;
import static ElementExtenter.ElementDocPage.scrollElementIntoView;
import static ElementExtenter.ElementDocPage.scrollToElementAndCenterVertically;
import static extensions.IReporter.addStepLog;

public class BaseUtils {

    public static WebDriverWait getWebDriverWaitCall(){
        return new WebDriverWait(getDrivers(), Duration.ofSeconds(ConfigLoader.hourglassTimeoutSec));
    }
    public static boolean waitForPageToLoad() {


        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        boolean b = getWebDriverWaitCall().until(jQueryLoad)
                && getWebDriverWaitCall().until(jsLoad);

        return getWebDriverWaitCall().until(jQueryLoad)
                && getWebDriverWaitCall().until(jsLoad);

    }

    public static WebElement toBeClickable(String by){
        waitForPageFinishLoad();
        getWebDriverWaitCall().until(ExpectedConditions.presenceOfAllElementsLocatedBy(getBy(by)));

        WebElement e = getWebDriverWaitCall().until(ExpectedConditions.elementToBeClickable(getBy(by)));

        return getWebDriverWaitCall().until(ExpectedConditions.visibilityOf(e));
    }

    public static boolean isElementPresent(String loc){
        disAbleImplicitWait();
        boolean b=false;

        for (int i = 0; i < 3; i++) {
            if (findEls(loc).size() > 0) {
                b = true;
            }
        }
        enableImplicitWait();

        return b;
    }

    public static void waitForSpinToDisappear(){
        disAbleImplicitWait();
        waitForSpinDisappear_Helper();
        enableImplicitWait();
    }

    public static boolean waitForSpinDisappear_Helper(){
        sleep(1000);
        ExpectedCondition<Boolean> pace_inactive = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                if(driver.findElement(By.cssSelector("span.spin-circle")).isEnabled()
                && driver.findElement(By.cssSelector("body[style='cursor: wait;']")).isEnabled()){
                    return false;
                }else{
                    return true;
                }
            }
        };
        return (getWebDriverWaitCall().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("span.spin-circle")))
        && getWebDriverWaitCall().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body[style='cursor: wait;']"))));
    }

    public static void waitForPageFinishLoad(){
        waitForPageToLoad();
        waitForSpinToDisappear();
    }

    public static WebElement highLightElement(WebElement element) {
        String js_str = "arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');";
        JavascriptExecutor js = (JavascriptExecutor) getDrivers();
        js.executeScript(js_str, element);
        return element;
    }

    public static WebElement findEl(@NotNull String ele) {
            return highLightElement($f(ele));
    }

    public static WebElement $conf(String val){
        val = (ConfigLoader.config.getString(val)!=null)?ConfigLoader.config.getString(val):val;
        return $f(val);
    }

    public static WebElement $f(String ele){
        return (getDrivers().findElement(
                getBy(ele)));
    }

    public static By getBy(String ele){
        if(isXpathLocatorValid(ele)) {
            return By.xpath(ele);
        }

        return By.cssSelector(ele);

    }

    public static WebElement list_search(String loc, String txt){

        for(WebElement e:findEls(loc)){
            if(e.isDisplayed()){
                e.sendKeys(txt);
                return e;
            }
        }
        return null;
    }

    public static WebElement sendKey(String loc,String str){
        findEl(loc).clear();
        findEl(loc).sendKeys(str);
        return findEl(loc);
    }

    public static boolean isEmptyString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isStringNotEmpty(String string) {
        return !(string == null) && !(string.trim().isEmpty());
    }

    public static void sleep(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebElement sendAction(String loc, String str){

        Actions action=new Actions (getDrivers());
        for(char c:str.toCharArray()) {

            action.moveToElement($f(loc)).sendKeys(c + "").build().perform();
            sleep(75);
        }
        return $f(loc);
    }

    public static WebElement scrollIntoView(String loc){
        return scrollElementIntoView(loc,getDrivers());
    }

    public static WebElement scrollToCenterVertically(String loc){
         scrollToElementAndCenterVertically($f(loc), getDrivers());
          return $f(loc);
    }

    public static WebElement scrollToVisible(String loc){
        $f(loc).sendKeys(Keys.END);
        $js("scroll(0, -250);");

        return $f(loc);
    }

    public static WebElement shouldScrollFromViewportByGivenAmount(String ele) {

        return scrollElementByAmount(findEl(ele));
    }

    public static WebElement scrollElementByAmount(WebElement ele) {

        int deltaY = ele.getRect().y;
        getActionsItem()
                .scrollByAmount(0, deltaY)
                .perform();

        return ele;
    }

    public static Actions getActionsItem(){
        return new Actions(getDrivers());
    }

    public static WebElement $js(String js, WebElement ele){
        JavascriptExecutor jse = (JavascriptExecutor)getDrivers();
        jse.executeScript(js, ele);
        return ele;
    }
    public static boolean inViewport(WebElement element) {

        String script =
                "for(var e=arguments[0],f=e.offsetTop,t=e.offsetLeft,o=e.offsetWidth,n=e.offsetHeight;\n"
                        + "e.offsetParent;)f+=(e=e.offsetParent).offsetTop,t+=e.offsetLeft;\n"
                        + "return f<window.pageYOffset+window.innerHeight&&t<window.pageXOffset+window.innerWidth&&f+n>\n"
                        + "window.pageYOffset&&t+o>window.pageXOffset";

        return (boolean) ((JavascriptExecutor) getDrivers()).executeScript(script, element);
    }

    public static void $js(String js){
        JavascriptExecutor jse = (JavascriptExecutor)getDrivers();
        jse.executeScript(js);
    }

    public static WebElement select(String loc, String txt){
        WebElement e = findEl(loc);
        Select sel = new Select(e);
        sel.selectByVisibleText(txt);
        addStepLog(String.format("Select value:%s for element:%s",txt,loc));
        return e;
    }



    public static List<WebElement> findEls(@NotNull String ele) {
        if (isXpathLocatorValid(ele) && ele.startsWith("//"))
            return (getDrivers().findElements(By.xpath(ele)));

        return (getDrivers().findElements(By.cssSelector(ele)));
    }

    public static Boolean isXpathLocatorValid(String xPath) {
        String js = "try{ "
                + "var elAmount = document.evaluate (arguments[0],document,null,"
                + "XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null).snapshotLength;"
                + "return elAmount;"
                + "}"
                + "catch(ex){"
                + "console.log(ex);"
                + "return -1;"
                + "}";
        return (xPath.startsWith("(//") || xPath.startsWith("//") || xPath.startsWith("./"))
                && (long)((JavascriptExecutor)getDrivers()).executeScript(js, xPath) >= 0;
    }

}
