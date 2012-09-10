package com.clickconcepts.junit.annotation;

public class ExpectedTestFailureException extends Throwable {
    public ExpectedTestFailureException(String message) {
        super(message);
    }
}
