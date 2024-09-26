package test;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;


public class TestManager {

    WebDriver driver;
    @BeforeSuite
    public void beforeSuite()
    {
        System.out.println("Suite Started: " );
    }

    @BeforeTest
    public void beforeTest()
    {
        System.out.println("Test Started: " );
    }

    @BeforeClass
    public void setUp()
    {
        System.out.println("Test Class Started: " );
         driver = new ChromeDriver();
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context, Method method)
    {
        System.out.println("Test Method Started: " + context.getName() + " Method: " + method.getName());
    }

    @AfterMethod
    public void tearDown(ITestContext context, Method method)
    {

        System.out.println("Test Method Ended: " + context.getName() + " Method: " + method.getName());

        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File("c:/rajkumar/testng_session/test-output/"+ method.getName()+".png");
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @AfterClass
    public void closeBrowser()
    {
        System.out.println("Test Class Ended: " );
        driver.quit();
    }

    @AfterTest
    public void afterTest()
    {
        System.out.println("Test Ended: " );

    }

    @AfterSuite
    public void afterSuite()
    {
        System.out.println("Suite Ended: " );
    }
}
