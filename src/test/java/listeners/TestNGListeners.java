package listeners;


import org.testng.*;


public class TestNGListeners implements IInvokedMethodListener, ITestListener, IExecutionListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println(">>> Before Method: " + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("<<< After Method: " + method.getTestMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[TEST FAILED] " + result.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[TEST STARTED] " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[TEST PASSED] " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[TEST SKIPPED] " + result.getName());
    }

    @Override
    public void onExecutionStart() {
        System.out.println("<== Test Execution Started ==>");
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("<== Test Execution Finished ==>");
    }
}