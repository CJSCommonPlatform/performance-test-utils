package uk.gov.justice.util.wildfly;


import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;
import java.util.List;

import static uk.gov.justice.util.JmxAttributesConstants.MEAN;

public class WildflyJmxFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WildflyJmxFactory.class);
    private static final double ZERO = 0.0;

    public static double timeTakenByCommandController(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(contextName)
                .append("*").toString();

        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByCommandHandler(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(contextName)
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByEventListenerAndProcessor(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByRestCommandApi(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-command-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByRestQueryApi(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByRestQueryController(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-controller/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    public static double timeTakenByRestQueryView(ExternalProperties props, String contextName)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getMetricsName())
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-view/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return getJmxAttributeValue(props, searchStr);
    }

    private static double getJmxAttributeValue(ExternalProperties props, String searchStr)
            throws MalformedObjectNameException, J4pException {
        J4pSearchRequest searchRequest = new J4pSearchRequest(searchStr);
        J4pSearchResponse searchResponse = props.getWildflyJ4PClient().execute(searchRequest);
        return getMeanTime(props, searchResponse.getMBeanNames());
    }

    private static double getMeanTime(ExternalProperties props, List<String> mBeanNames)
            throws MalformedObjectNameException, J4pException {
        double totalTime = ZERO;
        for (String name : mBeanNames) {
            try {
                J4pReadRequest req = new J4pReadRequest(name);
                J4pReadResponse resp = props.getWildflyJ4PClient().execute(req);
                totalTime = totalTime + (double) resp.getValue(MEAN);
            } catch (Exception ex) {
                LOGGER.error("mBean not found {}.", name);
                return ZERO;
            }
        }
        return totalTime;
    }
}


