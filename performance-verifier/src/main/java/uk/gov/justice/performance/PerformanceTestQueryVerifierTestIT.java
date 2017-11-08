package uk.gov.justice.performance;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.*;

@RunWith(Parameterized.class)
public class PerformanceTestQueryVerifierTestIT extends PerformanceVerifierBase {
    private Logger LOGGER = LoggerFactory.getLogger(PerformanceTestQueryVerifierTestIT.class);

    private static String[] contextNames = props.getProperty(CONTEXT_NAMES).split(COMMA);
    private static String[] properties = {
            FIFTEEN_MINUTE_RATE,
            FIFTIETH_PERCENTILE,
            FIVE_MINUTE_RATE,
            MAX,
            MEAN,
            MEAN_RATE,
            MIN,
            NINETY_EIGHTH_PERCENTILE,
            NINETY_FIFTH_PERCENTILE,
            NINETY_NINTH_PERCENTILE,
            NINE_HUNDRED_NINETY_NINTH_PERCENTILE,
            ONE_MINUTE_RATE,
            SEVENTY_FIFTH_PERCENTILE,
            STANDARD_DEVIATION
    };

    @Parameters(name = "context: {0} property: {1}")
    public static Collection<Object[]> testCases() {
        return Arrays.stream(contextNames)
                     .flatMap(n -> Arrays.stream(properties).map(p -> new Object[] { n, p }))
                     .collect(Collectors.toList());
    }

    @Parameter(0)
    public String contextName;

    @Parameter(1)
    public String property;

    @Test
    public void metricShouldBelowBreachingThreshold() throws Exception {
        MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, property);
        LOGGER.info("mbean name for queries || " + property + " || " + result.getName());

        String message = String.format("Query metric breached: $%s", result.getName());
        double actual = result.getTime();
        double threshold = Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN));
        assertThat(message, actual, lessThanOrEqualTo(threshold));
    }

}
