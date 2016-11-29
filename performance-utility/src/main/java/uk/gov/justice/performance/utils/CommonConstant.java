package uk.gov.justice.performance.utils;


public class CommonConstant {
    public static final String METRICS_NAME = "metrics.name";
    public static final String MEAN = "Mean";
    public static final String MEAN_RATE = "MeanRate";
    public static final String FIFTIETH_PERCENTILE = "50thPercentile";
    public static final String SEVENTY_FIFTH_PERCENTILE = "75thPercentile";
    public static final String NINETY_FIFTH_PERCENTILE = "95thPercentile";
    public static final String NINETY_EIGHTH_PERCENTILE = "98thPercentile";
    public static final String NINETY_NINTH_PERCENTILE = "99thPercentile";
    public static final String NINE_HUNDRED_NINETY_NINTH_PERCENTILE = "999thPercentile";
    public static final String FIFTEEN_MINUTE_RATE = "FifteenMinuteRate";
    public static final String FIVE_MINUTE_RATE = "FiveMinuteRate";
    public static final String ONE_MINUTE_RATE = "OneMinuteRate";
    public static final String MAX = "Max";
    public static final String MIN = "Min";
    public static final String COUNT = "Count";
    public static final String STANDARD_DEVIATION = "StdDev";
    public static final String DEV_PROXY_FULL_PATH = "dev.proxy.full.path";
    public static final String COMMA = ",";
    public static final String PROPERTY_FILE_NAME = "application.properties";
    public static final String CONTEXT_NAMES = "context.names";
    public static final String COMMAND_EXPECTED_TIME_TAKEN = "command.expected.time.taken";
    public static final String QUERY_EXPECTED_TIME_TAKEN = "query.expected.time.taken";
    public static final double ZERO = 0.0;

    private CommonConstant() {
        throw new IllegalAccessError("Utility class");
    }
}
