package ElementExtenter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static Utils.BaseUtils.$f;

public class ElementDocPage {

    public static Long getViewPortHeight(WebDriver driver)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        return (Long) je.executeScript("return window.innerHeight;");
    }

    public static List<String> getBoundedRectangleOfElement(WebElement we, WebDriver driver)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        List<String> bounds = (ArrayList<String>) je.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return [ '' + parseInt(rect.left), '' + parseInt(rect.top), '' + parseInt(rect.width), '' + parseInt(rect.height) ]", we);
//        System.out.println("left: " + bounds.get(0));
//        System.out.println("top: " + bounds.get(1));
//        System.out.println("width: " + bounds.get(2));
//        System.out.println("height: " + bounds.get(3));

        String s = """
                {
                "name":"testing"
                }
                """;
        return bounds;
    }

    public static void scrollToElementAndCenterVertically(WebElement we,WebDriver driver)
    {
        List<String> bounds = getBoundedRectangleOfElement(we,driver);
        Long totalInnerPageHeight = getViewPortHeight(driver);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("window.scrollTo(0, " + (Integer.parseInt(bounds.get(1)) - (totalInnerPageHeight/2)) + ");");
        outlineElement(we,driver);
    }

    public static void outlineElement(WebElement we, WebDriver driver)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].style.outline = \"thick solid #0000FF\";", we);
    }

    public static WebElement scrollElementIntoView(String loc, WebDriver driver){

        JavascriptExecutor je = (JavascriptExecutor) driver;

//        je.executeScript("arguments[0].scrollIntoView(true);",$f(loc));
        //arguments[0].scrollIntoView({behavior: "smooth", block: "center", inline: "nearest"}))
        je.executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"nearest\"});",$f(loc));
        return $f(loc);
    }
}
