package com.clickconcepts.junit.annotation;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ExpectedTestFailureWatcher implements TestRule {

    /**
     * Static factory to an instance of this watcher
     *
     * @return New instance of this watcher
     */
    public static ExpectedTestFailureWatcher instance() {
        return new ExpectedTestFailureWatcher();
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                boolean expectedToFail = description.getAnnotation(ExpectedFailure.class) != null;
                boolean failed = false;
                try {
                    // allow test case to execute
                    base.evaluate();
                } catch (Throwable exception) {
                    failed = true;
                    if (!expectedToFail) {
                        throw exception; // did not expect to fail and failed...fail
                    }
                }
                // placed outside of catch
                if (expectedToFail && !failed) {
                    throw new ExpectedTestFailureException(getUnFulfilledFailedMessage(description));
                }
            }

            /**
             * Extracts detailed message about why test failed
             * @param description
             * @return
             */
            private String getUnFulfilledFailedMessage(Description description) {
                String reason = null;
                if (description.getAnnotation(ExpectedFailure.class) != null) {
                    reason = description.getAnnotation(ExpectedFailure.class).reason();
                }
                if (isNullOrEmpty(reason)) {
                    reason = "Should have failed but didn't";
                }
                return reason;
            }
        };
    }
}