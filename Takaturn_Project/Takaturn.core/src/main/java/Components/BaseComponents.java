package Components;

import extensions.IReporter;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static Utils.BaseUtils.*;
import static extensions.IReporter.addStepLog;

public class BaseComponents {

    public void alertWindowBtnClick(String str) {
        waitForPageFinishLoad();

        if (str.equalsIgnoreCase("yes")) {
            findEl("button#yes").click();
        } else
            findEl("button#no").click();

    }

    public void multiSelectByText(String value) {

        WebElement el = null;
        for (WebElement e : findEls("span.dropdown-btn")) {
            if (e.isEnabled() && e.isDisplayed()) {
                el = e;
            }
        }
        el.click();
        for (String s : value.split(";")) {

            list_search("li.filter-textbox.ng-star-inserted input", s);
            waitForPageToLoad();
            $f(String.format("//li/div[text()='%s']", s)).click();
        }
        el.click();
    }

    public void verifyTxtInList(String ele, String value) {

        findEls(ele).stream().forEach(
                e -> {
                    if (e.isDisplayed() && e.getText().equalsIgnoreCase(value)) {
                        highLightElement(e);
                        Assert.assertTrue(!(e.getText().isEmpty()), "Text: " + value + " is not present in element:" + ele);
                    }
                }
        );
    }


    public enum SelectBy {
        TEXT,
        VALUE,
        INDEX
    }

    public void user_click_text(String txt) {
        waitForPageFinishLoad();
        findEl(String.format("//*[text()='%s']", txt)).click();
        IReporter.takeScreenShotForReport("User_Click");
        addStepLog(String.format("Use clicks at //*[text()='%s']",txt));
    }

    public void verifyTxt(String ele, String expected) {
        waitForPageFinishLoad();
        String actual = findEl(ele).getText();
        addStepLog("Element:" + ele);
        addStepLog(String.format("Assert Actual:%s with Expected:%s", actual, expected));
        //uncomment Assert.assertEquals(actual, expected);
    }

    public String stringNullEmptyOrNot(String val){

        if(val!=null){
            if(!val.isEmpty())
                return val;
        }
        return null;

    }

    public void sendDataKey(String ele, String value) {
        waitForPageFinishLoad();
        addStepLog(String.format("Set: %s at element: %s", value, ele));
        findEl(ele).clear();
        findEl(ele).sendKeys(value);
    }

    public WebElement sendkeyByAction(String ele, String value) {
        getActionsItem().moveToElement(findEl(ele)).click().perform();
        for (char c : value.toCharArray()) {
            getActionsItem().moveToElement(findEl(ele))
                    .sendKeys(c + "").perform();
            sleep(75);
        }
        addStepLog(String.format("SendKey By Action value:%s element:%s",value,ele));
        return $f(ele);
    }

    public WebElement sendKeyByJs(String ele, String value) {
        addStepLog(String.format("SendKey By JS value:%s element:%s",value,ele));
        return $js("arguments[0].value='" + value + "'", findEl(ele));
// set the text
//        jsExecutor.executeScript("arguments[0].value='testuser'", userNameTxt);
////get the text
//        String text = (String) jsExecutor.executeScript("return arguments[0].value", userNameTxt);
//        System.out.println(text);
    }

    public void onlyif_sendDataKey(String ele, String value) {
        waitForPageFinishLoad();
        addStepLog(String.format("OnlyIf-Set: %s at element: %s", value, ele));
        if ((findEls(ele).size() > 0) && findEl(ele).isEnabled())
            findEl(ele).sendKeys(value);
        else addStepLog(String.format("element: %s is Skipped", ele));
    }

    public void verifyPageTile(String exp, String actual){
        waitForPageFinishLoad();
        addStepLog(String.format("Page gets loaded"));
    }

}
