package uk.gov.justice.util.wildfly;


import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import uk.gov.justice.util.ExternalProperties;

import javax.management.MalformedObjectNameException;
import java.util.List;

import static uk.gov.justice.util.JmxAttributesConstants.MEAN;

public class WildflyJmxFactory {

    public static double timeTakenByCommandController(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.jms.queue."
                + externalProperties.getContextName()
                + ".controller.command-"
                + externalProperties.getContextName()
                + "*";

        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByCommandHandler(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.jms.queue."
                + externalProperties.getContextName()
                + ".handler.command-"
                + externalProperties.getContextName()
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByEventListenerAndProcessor(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.jms.topic."
                + externalProperties.getContextName()
                + ".event-"
                + externalProperties.getContextName()
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByRestCommandApi(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.rest."
                + externalProperties.getContextName()
                + "-command-api/"
                + externalProperties.getContextName()
                + "/"
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByRestQueryApi(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.rest."
                + externalProperties.getContextName()
                + "-query-api/"
                + externalProperties.getContextName()
                + "/"
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByRestQueryController(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.rest."
                + externalProperties.getContextName()
                + "-query-controller/"
                + externalProperties.getContextName()
                + "/"
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    public static double timeTakenByRestQueryView(ExternalProperties externalProperties) throws J4pException, MalformedObjectNameException {
        String searchStr = externalProperties.getMetricsName()
                + ":name=wildfly.rest."
                + externalProperties.getContextName()
                + "-query-view/"
                + externalProperties.getContextName()
                + "/"
                + "*";
        return getJmxAttributeValue(externalProperties, searchStr);
    }

    private static double getJmxAttributeValue(ExternalProperties externalProperties, String searchStr) throws MalformedObjectNameException, J4pException {
        J4pSearchRequest searchRequest = new J4pSearchRequest(searchStr);
        J4pSearchResponse searchResponse = externalProperties.getWildflyJ4PClient().execute(searchRequest);
        return getMeanTime(externalProperties, searchResponse.getMBeanNames());
    }

    private static double getMeanTime(ExternalProperties externalProperties, List<String> mbeanNames) throws MalformedObjectNameException, J4pException {
        double totalTime = 0.0;
        for (String name : mbeanNames) {
            J4pReadRequest req = new J4pReadRequest(name);
            J4pReadResponse resp = externalProperties.getWildflyJ4PClient().execute(req);
            totalTime += (Double) resp.getValue(MEAN);
        }
        return totalTime;
    }

}
