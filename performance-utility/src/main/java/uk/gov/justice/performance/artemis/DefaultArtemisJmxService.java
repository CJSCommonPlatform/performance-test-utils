package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.utils.JolokiaArtemisClient;

import javax.management.MalformedObjectNameException;

public class DefaultArtemisJmxService implements ArtemisJmxService {
    private static final String METRICS_NAME = "metrics.name";
    private ExternalProperties props = ExternalProperties.getInstance();
    private JolokiaArtemisClient jolokiaArtemisClient = JolokiaArtemisClient.getInstance();

    public double timeMessageStaysInCommandQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".controller.command").toString();
        return jolokiaArtemisClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double timeMessageStaysInHandlerQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".handler.command").toString();
        return jolokiaArtemisClient.getJmxAttributeValue(mBeanName, timeType);
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
        return jolokiaArtemisClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double totalTimeMessageStaysInQueuesAndTopic(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        return timeMessageStaysInCommandQueue(contextName, timeType)
                + timeMessageStaysInHandlerQueue(contextName, timeType)
                + timeMessageStaysInEventListenerTopic(contextName, timeType);
    }
}
