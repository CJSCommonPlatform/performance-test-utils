package uk.gov.justice.util.artemis;


import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;

import static uk.gov.justice.util.JmxAttributesConstants.MEAN;

public class ArtemisJmxFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisJmxFactory.class);
    public static final double ZERO = 0.0;

    public static double timeMessageStaysInCommandQueue(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getMetricsName())
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".controller.command").toString();
        return getJmxAttributeValue(props, mBeanName);
    }

    public static double timeMessageStaysInHandlerQueue(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getMetricsName())
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".handler.command").toString();
        return getJmxAttributeValue(props, mBeanName);
    }

    public static double timeMessageStaysInEventListenerQueue(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getMetricsName())
                .append(":name=jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("\\.event\\.listener\\.")
                .append(contextName)
                .append("\\.event").toString();
        return getJmxAttributeValue(props, mBeanName);
    }

    private static double getJmxAttributeValue(ExternalProperties props, String mBeanName) {
        try {
            J4pReadRequest req = new J4pReadRequest(mBeanName);
            J4pReadResponse resp = props.getArtemisJ4PClient().execute(req);
            return resp.getValue(MEAN);
        } catch (Exception ex) {
            LOGGER.error("mBean not found {}.", mBeanName);
            return ZERO;
        }
    }
}
