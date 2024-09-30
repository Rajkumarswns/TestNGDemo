package test;

import org.testng.IRetryAnalyzer;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

// Interface helps you to hvae a new behaviour in your own way....
public class FailureHandler implements IRetryAnalyzer {

    private static final int MAX_RETRY = 3;
    private int retryCount = 1;
    @Override
    public boolean retry(ITestResult iTestResult) {

       if(!iTestResult.isSuccess())
       {
           this.retryCount+=1;
              if(this.retryCount<=MAX_RETRY)
              {
                System.out.println(" Failure Handler Check: Test Failed, Retrying: " + iTestResult.getName() + " for " + this.retryCount + " time");
                return true;
              }
       }
        return false;
    }
}
