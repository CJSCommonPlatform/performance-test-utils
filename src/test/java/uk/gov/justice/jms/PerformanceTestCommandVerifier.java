package uk.gov.justice.jms;


import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInCommandQueue;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInEventListenerQueue;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInHandlerQueue;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByRestCommandApi;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByCommandController;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByCommandHandler;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByEventListenerAndProcessor;

public class PerformanceTestCommandVerifier {

    private ExternalProperties props = new ExternalProperties();

    @Test
    public void shouldHaveTotalTimeLessThanEqualToExpectedTime() throws Exception {
        assertThat(artemisTimeForCommands() + wildflyTimeForCommands(),
                lessThanOrEqualTo(props.getExpectedTimeTakenByCommands()));
    }

    private double artemisTimeForCommands() throws J4pException, MalformedObjectNameException {
        return timeMessageStaysInCommandQueue(props) + timeMessageStaysInHandlerQueue(props)
                + timeMessageStaysInEventListenerQueue(props);
    }

    private double wildflyTimeForCommands() throws J4pException, MalformedObjectNameException {
        return timeTakenByRestCommandApi(props) + timeTakenByCommandController(props)
                + timeTakenByCommandHandler(props) + timeTakenByEventListenerAndProcessor(props);
    }
}
