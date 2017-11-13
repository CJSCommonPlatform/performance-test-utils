package uk.gov.justice.performance;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.COMMAND_EXPECTED_TIME_TAKEN;
import static uk.gov.justice.performance.utils.CommonConstant.CONTEXT_NAMES;
import static uk.gov.justice.performance.utils.CommonConstant.MEAN;

@RunWith(Parameterized.class)
public class PerformanceTestCommandVerifierTestIT extends PerformanceVerifierBase {
    private Logger LOGGER = LoggerFactory.getLogger(PerformanceTestCommandVerifierTestIT.class);

    @Parameters(name = "context: {0}")
    public static String[] contextNames() {
        return props.getProperty(CONTEXT_NAMES).split(COMMA);
    }

    @Parameter
    public String contextName;

    @Test
    public void shouldHaveTotalMeanTimeLessThanEqualToExpectedTime() throws Exception {
        MBean timeInQueues = artemisJmxService.totalTimeMessageStaysInQueuesAndTopic(contextName, MEAN);
        MBean timeForWildfly = wildflyJmxService.totalWildflyTimeForCommands(contextName, MEAN);

        LOGGER.info("name for QueuesAndTopic : MEAN " + timeInQueues.getName());
        LOGGER.info("name for Commands : " + timeForWildfly.getName());

        double actual = timeInQueues.getTime() + timeForWildfly.getTime();
        double threshold = Double.parseDouble(props.getProperty(COMMAND_EXPECTED_TIME_TAKEN));

        String message = String.format(
                "Command threshold breached: %s, %s",
                timeInQueues.getName(),
                timeForWildfly.getName());
        assertThat(message, actual, lessThanOrEqualTo(threshold));
    }
}
