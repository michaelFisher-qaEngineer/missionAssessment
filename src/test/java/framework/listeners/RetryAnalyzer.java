package framework.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;


public class RetryAnalyzer implements IRetryAnalyzer {
	private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);

    private int maxRetryCount = 1; // number of retries (1 retry = up to 2 total attempts)
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        // Only retry @UI scenarios tagged @flakey
    	if (!hasTag(result, "@UI") || !hasTag(result, "@flakey")) {
    		return false;
        }

        if (retryCount < maxRetryCount) {
            retryCount++;
            logger.debug("Retrying @flakey scenario '{}' ({}/{})",
                    result.getName(), retryCount, maxRetryCount);
            return true;
        }

        logger.debug("@flakey scenario '{}' failed after {} attempts",
                result.getName(), retryCount + 1);
        return false;
    }

    private boolean hasTag(ITestResult result, String tagName) {

        Object[] params = result.getParameters();
        if (params == null) {
            return false;
        }

        for (int i = 0; i < params.length; i++) {
            Object p = params[i];

            // Cucumber-TestNG passes (PickleWrapper, FeatureWrapper)
            if (p instanceof PickleWrapper) {
                PickleWrapper pickleWrapper = (PickleWrapper) p;

                for (String tag : pickleWrapper.getPickle().getTags()) {
                    if (tagName.equals(tag)) {
                        return true;
                    }
                }
                return false;
            }

            // (Optional) ignore FeatureWrapper; present for completeness
            if (p instanceof FeatureWrapper) {
                // no-op
            }
        }

        return false;
    }

}
