package uk.gov.justice.it;


import org.junit.Test;
import uk.gov.justice.performance.artemis.ArtemisJmxService;
import uk.gov.justice.performance.artemis.DefaultArtemisJmxService;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.utils.JmxAttributesConstant.MEAN;

public class PerformanceTestCommandVerifier {

    private ArtemisJmxService artemisJmxService = new DefaultArtemisJmxService();
    private WildflyJmxService wildflyJmxService = new DefaultWildflyJmxService();

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTime() throws Exception {
        String[] names = new String[]{"people", "usersgroups"};

        for (String contextName : names) {
            double timeInQueues = artemisJmxService.timeMessageStaysInCommandQueue(contextName, MEAN)
                    + artemisJmxService.timeMessageStaysInHandlerQueue(contextName, MEAN)
                    + artemisJmxService.timeMessageStaysInEventListenerTopic(contextName, MEAN);

            double timeForWildfly = wildflyJmxService.timeTakenByCommandController(contextName, MEAN)
                    + wildflyJmxService.timeTakenByCommandHandler(contextName, MEAN)
                    + wildflyJmxService.timeTakenByEventListener(contextName, MEAN);

            assertThat(timeInQueues + timeForWildfly, lessThanOrEqualTo(5000.0));
        }
    }
}
