package uk.gov.justice.it;


import org.junit.Test;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.COUNT;
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
import static uk.gov.justice.performance.utils.CommonConstant.SEVENTY_FIFTH_PERCENTILE;
import static uk.gov.justice.performance.utils.CommonConstant.STANDARD_DEVIATION;

public class PerformanceTestQueryVerifierTest {

    private static final String COMMAND_EXPECTED_TIME_TAKEN = "query.expected.time.taken";
    private static final String CONTEXT_NAMES = "context.names";
    public static final String PEOPLE_CONTEXT = "people";
    private WildflyJmxService wildflyJmxService = new DefaultWildflyJmxService();
    private ExternalProperties externalProperties = ExternalProperties.getInstance();

    //test for multiple contexts
    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedMeanTime() throws Exception {
        String[] names = externalProperties.value(CONTEXT_NAMES).split(COMMA);
        for (String contextName : names)
            assertThat(wildflyJmxService.totalWildflyTimeForQueries(contextName, MEAN),
                    lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalMeanRateLessThanEqualToExpectedMeanRate() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MEAN_RATE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalSeventyFifthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, SEVENTY_FIFTH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalFiftiethPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIFTIETH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalNinetyFifthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_FIFTH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalNinetyEighthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_EIGHTH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_NINTH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpectedNineHundred() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINE_HUNDRED_NINETY_NINTH_PERCENTILE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalFifteenMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIFTEEN_MINUTE_RATE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalFiveMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIVE_MINUTE_RATE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveTotalOneMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, ONE_MINUTE_RATE),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveMaxTimeLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MAX),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveMinLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MIN),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveCountLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, COUNT),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }

    @Test
    public void shouldHaveStandardDeviationLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, STANDARD_DEVIATION),
                lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
    }
}
