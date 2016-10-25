package uk.gov.justice.jms;


import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByRestQueryApi;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByRestQueryController;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByRestQueryView;

public class PerformanceTestQueryVerifier {

    private ExternalProperties props = new ExternalProperties();

    @Test
    public void shouldHaveTotalTimeLessThanEqualToExpectedTime() throws Exception {
        for (String contextName : props.getContextNames()) {
            assertThat(wildflyTimeForQueries(contextName), lessThanOrEqualTo(props.getExpectedTimeTakenByQueries()));
        }
    }

    private double wildflyTimeForQueries(String contextName) throws J4pException, MalformedObjectNameException {
        return timeTakenByRestQueryApi(props, contextName) + timeTakenByRestQueryController(props, contextName)
                + timeTakenByRestQueryView(props, contextName);
    }
}
