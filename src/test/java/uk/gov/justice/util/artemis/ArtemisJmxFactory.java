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

    public static double timeMessageStaysInCommandQueue(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String mBeanName = externalProperties.getMetricsName()
                + ":name=jms.queue."
                + externalProperties.getContextName()
                + ".controller.command";
        return getJmxAttributeValue(externalProperties, mBeanName);
    }

    public static double timeMessageStaysInHandlerQueue(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String mBeanName = externalProperties.getMetricsName() + ":name=jms.queue."
                + externalProperties.getContextName()
                + ".handler.command";
        return getJmxAttributeValue(externalProperties, mBeanName);
    }

    public static double timeMessageStaysInEventListenerQueue(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String mBeanName = externalProperties.getMetricsName() + ":name=jms.topic."
                + externalProperties.getContextName() + ".event-"
                + externalProperties.getContextName() + "\\.event\\.listener\\."
                + externalProperties.getContextName() + "\\.event";
        return getJmxAttributeValue(externalProperties, mBeanName);
    }

    private static double getJmxAttributeValue(ExternalProperties externalProperties, String mBeanName) {
        try {
            J4pReadRequest req = new J4pReadRequest(mBeanName);
            J4pReadResponse resp = externalProperties.getArtemisJ4PClient().execute(req);
            return resp.getValue(MEAN);
        } catch (Exception ex) {
            LOGGER.trace("mBean not found {}.", mBeanName);
            return ZERO;
        }
    }
}
