package uk.gov.justice.performance;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.CONTEXT_NAMES;
import static uk.gov.justice.performance.utils.CommonConstant.FIFTEEN_MINUTE_RATE;
import static uk.gov.justice.performance.utils.CommonConstant.FIFTIETH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.FIVE_MINUTE_RATE;
import static uk.gov.justice.performance.utils.CommonConstant.MAX;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN_RATE;
import static uk.gov.justice.performance.utils.CommonConstant.MIN;
import static uk.gov.justice.performance.utils.CommonConstant.NINETY_EIGHTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.NINETY_FIFTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.NINETY_NINTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.NINE_HUNDRED_NINETY_NINTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.ONE_MINUTE_RATE;
import static uk.gov.justice.performance.utils.CommonConstant.QUERY_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.SEVENTY_FIFTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.STANDARD_DEVIATION;

public class PerformanceTestQueryVerifierTestIT extends PerformanceVerifierBase {
    private Logger LOGGER = LoggerFactory.getLogger(PerformanceTestQueryVerifierTestIT.class);
    String[] names = props.getProperty(CONTEXT_NAMES).split(COMMA);

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedMeanTime() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, MEAN);
            LOGGER.info("mbean name for queries || " + MEAN + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalMeanRateLessThanEqualToExpectedMeanRate() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, MEAN_RATE);
            LOGGER.info("mbean name for queries || " + MEAN_RATE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalSeventyFifthPercentileLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, SEVENTY_FIFTH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + SEVENTY_FIFTH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalFiftiethPercentileLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, FIFTIETH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + FIFTIETH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalNinetyFifthPercentileLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, NINETY_FIFTH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + NINETY_FIFTH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalNinetyEighthPercentileLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, NINETY_EIGHTH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + NINETY_EIGHTH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, NINETY_NINTH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + NINETY_NINTH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpectedNineHundred() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, NINE_HUNDRED_NINETY_NINTH_PERCENTILE);
            LOGGER.info("mbean name for queries || " + NINE_HUNDRED_NINETY_NINTH_PERCENTILE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalFifteenMinuteRateLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, FIFTEEN_MINUTE_RATE);
            LOGGER.info("mbean name for queries || " + FIFTEEN_MINUTE_RATE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalFiveMinuteRateLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, FIVE_MINUTE_RATE);
            LOGGER.info("mbean name for queries || " + FIVE_MINUTE_RATE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalOneMinuteRateLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, ONE_MINUTE_RATE);
            LOGGER.info("mbean name for queries || " + ONE_MINUTE_RATE + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveMaxTimeLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, MAX);
            LOGGER.info("mbean name for queries || " + MAX + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveMinLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, MIN);
            LOGGER.info("mbean name for queries || " + MIN + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveStandardDeviationLessThanEqualToExpected() throws Exception {
        for (String contextName : names) {
            MBean result = wildflyJmxService.totalWildflyTimeForQueries(contextName, STANDARD_DEVIATION);
            LOGGER.info("mbean name for queries || " + STANDARD_DEVIATION + " || " + result.getName());
            assertThat(result.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(QUERY_EXPECTED_TIME_TAKEN))));
        }
    }
}
