package com.clickconcepts.junit.annotation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExpectedTestFailureExceptionTest {

    @Test
    public void basicCreate() {
        ExpectedTestFailureException exception = new ExpectedTestFailureException("TEST");
        assertThat(exception.getMessage(), is("TEST"));
    }
}
