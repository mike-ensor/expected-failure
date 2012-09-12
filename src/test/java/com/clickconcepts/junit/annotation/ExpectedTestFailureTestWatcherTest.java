package com.clickconcepts.junit.annotation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpectedTestFailureTestWatcherTest {

    private ExpectedTestFailureWatcher rule = ExpectedTestFailureWatcher.instance();
    @Mock
    private Statement base;
    @Mock
    private Description description;

    @Test(expected = ExpectedTestFailureException.class)
    public void expectedButNoFailure() throws Throwable {
        mockAnnotatedTestCase();
        runTestCase();
    }

    @Test
    public void notExpectedNoFailure() throws Throwable {
        runTestCase();
    }

    @Test(expected = IllegalAccessException.class)
    public void notExpectedWithFailure() throws Throwable {
        mockTestCaseFailure(new IllegalAccessException("Illegal Access"));
        runTestCase();
    }

    @Test
    public void expectedWithKnownFailure_simulateExpectedTestToFail() throws Throwable {
        mockTestCaseFailure(new IllegalAccessException("Illegal Access"));
        mockAnnotatedTestCase();
        runTestCase();
    }

    private void mockTestCaseFailure(final Exception exception) throws Throwable {
        doThrow(exception).when(base).evaluate();
    }

    private void mockAnnotatedTestCase() {
        ExpectedFailure annotationInstance = mock(ExpectedFailure.class);
        when(description.getAnnotation(ExpectedFailure.class)).thenReturn(annotationInstance);
    }

    private void runTestCase() throws Throwable {
        rule.apply(base, description).evaluate();
    }

}