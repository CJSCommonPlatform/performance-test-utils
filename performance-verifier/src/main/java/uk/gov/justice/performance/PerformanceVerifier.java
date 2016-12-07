package uk.gov.justice.performance;

public class PerformanceVerifier {
    private static String[] tests = {
      PerformanceTestCommandVerifierTestIT.class.getName(), 
      PerformanceTestQueryVerifierTestIT.class.getName() };

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main(tests);
    }
}
