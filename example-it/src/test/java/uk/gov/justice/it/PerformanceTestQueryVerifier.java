package uk.gov.justice.it;


import org.junit.Test;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.utils.JmxAttributesConstant.*;

public class PerformanceTestQueryVerifier {

    private static final double EXPECTED_VALUE = 5000.0; //in ms
    public static final String PEOPLE_CONTEXT = "people";
    public static final String USERSGROUPS_CONTEXT = "usersgroups";
    private WildflyJmxService wildflyJmxService = new DefaultWildflyJmxService();

    //test for multiple contexts
    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedMeanTime() throws Exception {
        String[] names = new String[]{PEOPLE_CONTEXT, USERSGROUPS_CONTEXT};
        for (String contextName : names)
            assertThat(wildflyJmxService.totalWildflyTimeForQueries(contextName, MEAN), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalMeanRateLessThanEqualToExpectedMeanRate() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MEAN_RATE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalSeventyFifthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, SEVENTY_FIFTH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalFiftiethPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIFTIETH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalNinetyFifthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_FIFTH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalNinetyEighthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_EIGHTH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINETY_NINTH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalNinetyNinthPercentileLessThanEqualToExpectedNineHundred() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, NINE_HUNDRED_NINETY_NINTH_PERCENTILE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalFifteenMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIFTEEN_MINUTE_RATE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalFiveMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, FIVE_MINUTE_RATE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveTotalOneMinuteRateLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, ONE_MINUTE_RATE), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveMaxTimeLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MAX), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveMinLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, MIN), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveCountLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, COUNT), lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldHaveStandardDeviationLessThanEqualToExpected() throws Exception {
        assertThat(wildflyJmxService.totalWildflyTimeForQueries(PEOPLE_CONTEXT, STANDARD_DEVIATION), lessThanOrEqualTo(EXPECTED_VALUE));
    }
}
