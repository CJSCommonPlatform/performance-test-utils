package uk.gov.justice.it;


import org.junit.Test;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.utils.JmxAttributesConstant.MEAN;

public class PerformanceTestQueryVerifier {

    private WildflyJmxService wildflyJmxService = new DefaultWildflyJmxService();

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedMeanTime() throws Exception {
        String[] names = new String[]{"people", "usersgroups"};
        for (String contextName : names)
            assertThat(wildflyJmxService.totalWildflyTimeForQueries(contextName, MEAN), lessThanOrEqualTo(5000.0));
    }
}
