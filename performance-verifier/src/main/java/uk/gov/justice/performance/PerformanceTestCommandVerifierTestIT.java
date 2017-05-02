package uk.gov.justice.performance;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.COMMAND_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.CONTEXT_NAMES;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;

public class PerformanceTestCommandVerifierTestIT extends PerformanceVerifierBase {
    private Logger LOGGER = LoggerFactory.getLogger(PerformanceTestCommandVerifierTestIT.class);

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTime() throws Exception {
        String[] names = props.getProperty(CONTEXT_NAMES).split(COMMA);

        for (String contextName : names) {

            MBean timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
            MBean timeForWildfly = wildflyJmxService.totalWildflyTimeForCommands(contextName, MEAN);

            LOGGER.info("name for QueuesAndTopic : MEAN " + timeInQueues.getName());
            LOGGER.info("name for Commands : " + timeForWildfly.getName());

            assertThat(timeInQueues.getTime() + timeForWildfly.getTime(),
                    lessThanOrEqualTo(Double.parseDouble(props.getProperty(COMMAND_EXPECTED_TIME_TAKEN))));
        }
    }
}
