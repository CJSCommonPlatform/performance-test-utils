package uk.gov.justice.performance;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.COMMAND_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.CONTEXT_NAMES;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;
import static uk.gov.justice.performance.utils.CommonConstant.SPECIFIC_COMMAND_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.SPECIFIC_COMMAND_NAME;
import static uk.gov.justice.performance.utils.CommonConstant.SPECIFIC_COMMAND_REST_END_POINT;

import org.junit.Test;

public class PerformanceTestCommandVerifierTestIT extends PerformanceVerifierBase {

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTimeForAllCommands() throws Exception {
        String[] names = props.getProperty(CONTEXT_NAMES).split(COMMA);

        for (String contextName : names) {

            double timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
            double timeForWildfly = wildflyJmxService.totalWildflyTimeForCommands(contextName, MEAN);

            assertThat(timeInQueues + timeForWildfly,
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(COMMAND_EXPECTED_TIME_TAKEN))));
        }
    }

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTimeForSpecificCommands() throws Exception {
        String contextName = props.getProperty(CONTEXT_NAMES).split(COMMA)[0];

        double timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
        double timeForWildfly = wildflyJmxService.totalWildflyTimeForSpecificCommand(contextName,
                props.getProperty(SPECIFIC_COMMAND_NAME), props.getProperty(SPECIFIC_COMMAND_REST_END_POINT),
                MEAN);
        assertThat(timeInQueues + timeForWildfly,
                lessThanOrEqualTo(Double.parseDouble(props.getProperty(SPECIFIC_COMMAND_EXPECTED_TIME_TAKEN))));
    }
}
