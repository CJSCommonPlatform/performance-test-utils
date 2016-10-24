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
    public void shouldHaveTotalTimeLessThanEqualTo5SecondsForQueries() throws Exception {
        assertThat(wildflyTimeForQueries(), lessThanOrEqualTo(props.getExpectedTimeTakenByQueries()));
    }

    private double wildflyTimeForQueries() throws J4pException, MalformedObjectNameException {
        return timeTakenByRestQueryApi(props) + timeTakenByRestQueryController(props) + timeTakenByRestQueryView(props);
    }
}
