package uk.gov.justice.performance;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.COMMAND_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.CONTEXT_NAMES;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;

import org.junit.Test;

public class PerformanceTestCommandVerifierTestIT extends PerformanceVerifierBase {

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTime() throws Exception {
        String[] names = props.getProperty(CONTEXT_NAMES).split(COMMA);

        for (String contextName : names) {

            double timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
            double timeForWildfly = wildflyJmxService.totalWildflyTimeForCommands(contextName, MEAN);

            assertThat(timeInQueues + timeForWildfly,
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(COMMAND_EXPECTED_TIME_TAKEN))));
        }
    }
}
