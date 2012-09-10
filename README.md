This project creates a @Rule object to govern test cases and then provides the ability to annotate a test case with @ExpectedFailure allowing a test case that would fail to succeed.

In this example, the test case would normally fail given that the "exception" would fail based on the assertion in the exception assertion object

    public class ExceptionAssertTest {

        @Rule
        public ExpectedException exception = ExpectedException.none();

        @Rule
        public ExpectedTestFailureWatcher expectedTestFailureWatcher = ExpectedTestFailureWatcher.instance();

        @Test
        @ExpectedFailure("The matcher should fail becasue exception is not a SimpleException")
        public void assertSimpleExceptionAssert_exceptionIsOfType() {
            // expected exception will be of type "SimpleException"
            exception.instanceOf(SimpleException.class);
            // throw something other than SimpleException...expect failure
            throw new RuntimeException("this is an exception");
        }
    }

