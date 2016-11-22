package uk.gov.justice.it;


import org.junit.Test;
import uk.gov.justice.performance.artemis.ArtemisJmxService;
import uk.gov.justice.performance.artemis.DefaultArtemisJmxService;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;

public class PerformanceTestCommandVerifier {
    private static final String COMMAND_EXPECTED_TIME_TAKEN = "command.expected.time.taken";
    private static final String CONTEXT_NAMES = "context.names";
    private ArtemisJmxService artemisJmxService = new DefaultArtemisJmxService();
    private WildflyJmxService wildflyJmxService = new DefaultWildflyJmxService();
    private ExternalProperties externalProperties = ExternalProperties.getInstance();

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTime() throws Exception {
        String[] names = externalProperties.value(CONTEXT_NAMES).split(COMMA);

        for (String contextName : names) {

            double timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
            double timeForWildfly = wildflyJmxService.totalWildflyTimeForCommands(contextName, MEAN);

            assertThat(timeInQueues + timeForWildfly,
                    lessThanOrEqualTo(Double.parseDouble(externalProperties.value(COMMAND_EXPECTED_TIME_TAKEN))));
        }
    }
}
