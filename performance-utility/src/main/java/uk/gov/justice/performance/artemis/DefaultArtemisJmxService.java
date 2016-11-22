package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.utils.ArtemisJolokiaClient;
import uk.gov.justice.performance.utils.ExternalProperties;

import javax.management.MalformedObjectNameException;

import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;

public class DefaultArtemisJmxService implements ArtemisJmxService {

    private ExternalProperties props = ExternalProperties.getInstance();
    private ArtemisJolokiaClient artemisJolokiaClient = ArtemisJolokiaClient.getInstance();

    public double timeMessageStaysInCommandQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".controller.command").toString();
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double timeMessageStaysInHandlerQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".handler.command").toString();
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double timeMessageStaysInEventListenerTopic(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("\\.event\\.listener\\.")
                .append(contextName)
                .append("\\.event").toString();
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double totalTimeMessageStaysInQueuesAndTopic(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        return timeMessageStaysInCommandQueue(contextName, timeType)
                + timeMessageStaysInHandlerQueue(contextName, timeType)
                + timeMessageStaysInEventListenerTopic(contextName, timeType);
    }
}
