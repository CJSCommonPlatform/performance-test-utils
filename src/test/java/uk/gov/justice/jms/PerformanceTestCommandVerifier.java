package uk.gov.justice.jms;


import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInCommandQueue;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInEventListenerTopic;
import static uk.gov.justice.util.artemis.ArtemisJmxFactory.timeMessageStaysInHandlerQueue;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByCommandController;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByCommandHandler;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByEventListenerAndProcessor;
import static uk.gov.justice.util.wildfly.WildflyJmxFactory.timeTakenByRestCommandApi;

public class PerformanceTestCommandVerifier {
    private ExternalProperties props = new ExternalProperties();

    @Test
    public void shouldHaveTotalTimeLessThanEqualToExpectedTime() throws Exception {
        for (String contextName : props.getContextNames()) {
            assertThat(artemisTimeForCommands(contextName) + wildflyTimeForCommands(contextName),
                    lessThanOrEqualTo(props.getExpectedTimeTakenByCommands()));
        }
    }

    private double artemisTimeForCommands(String contextName) throws J4pException, MalformedObjectNameException {
        return timeMessageStaysInCommandQueue(props, contextName) + timeMessageStaysInHandlerQueue(props, contextName)
                + timeMessageStaysInEventListenerTopic(props, contextName);
    }

    private double wildflyTimeForCommands(String contextName) throws J4pException, MalformedObjectNameException {
        return timeTakenByRestCommandApi(props, contextName) + timeTakenByCommandController(props, contextName)
                + timeTakenByCommandHandler(props, contextName) + timeTakenByEventListenerAndProcessor(props, contextName);
    }
}
