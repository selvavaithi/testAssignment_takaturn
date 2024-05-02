package extensions;

import Common.ConfigLoader;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import static DriverUtils.DriverAgent.getDrivers;

public class IReporter {

    public static void addStepLog(String txt){
        ExtentCucumberFormatter.addTestStepLog(String.format("--> <span style=\"font-weight:lighter\">%s</span><br/>", txt));
                //.getLogs().add(String.format("\t\t<span style=\"color:blue;font-weight:bold\">%s</span><br/>", txt));

    }

    public static void takeScreenShotForReport(String name){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_SSS");
        String screenShotName = replaceSpecialCharsInMethodFileName(name)+"_"+simpleDateFormat.format(calendar.getTime());
        String filePath = System.getProperty("user.dir")+"/"+ ConfigLoader.screenShotsFolder+"/"+screenShotName+".png";
        String srcFilePath=System.getProperty("user.dir")+"/target/test-output/extentReport/"+screenShotName+".png";

        String relativePath = "./"+screenShotName+".png";
        try {
            //File srcFolder = new File(srcFilePath);
            //takeSnapShot(getDrivers(),filePath);
            String base64Screen = takeSnapShot(getDrivers(), srcFilePath);
//            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(srcFilePath,name);
//            ExtentCucumberAdapter.getCurrentStep().info("Taking_ScreenShote");
            ExtentCucumberAdapter.getCurrentStep().log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screen).build());

//            System.out.println("TAKESCREENSHOTE:"+relativePath);
//            System.out.println("ScreenShote FILEPATH:"+filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static String replaceSpecialCharsInMethodFileName(String methodName){
        return methodName.replaceAll("[^a-zA-Z0-9\\.\\-]","_");
    }

    public static String takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        String Base64StringofScreenshot="";
        File src = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = FileUtils.readFileToByteArray(src);
        Base64StringofScreenshot = "data:image/png;base64,"+ Base64.getEncoder().encodeToString(fileContent);

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        // C:\Users\selva\repoworkspace\cucumber-testng\Landy_Insurance-Testing-v1\litaf.test\target\test-output\extentReport

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile=new File(fileWithPath);

        //Copy file at destination

        FileUtils.copyFile(SrcFile, DestFile);

        return Base64StringofScreenshot;

    }
}
