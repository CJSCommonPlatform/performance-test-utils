package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.utils.JolokiaWildflyClient;

import javax.management.MalformedObjectNameException;

public class DefaultWildflyJmxService implements WildflyJmxService {
    private static final String METRICS_NAME = "metrics.name";

    private ExternalProperties props = ExternalProperties.getInstance();

    private JolokiaWildflyClient jolokiaWildflyClient = JolokiaWildflyClient.getInstance();


    public double timeTakenByCommandController(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(contextName)
                .append("*").toString();

        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByCommandHandler(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(contextName)
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByEventListenerAndProcessor(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByRestCommandApi(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-command-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByRestQueryApi(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByRestQueryController(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-controller/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByRestQueryView(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-view/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return jolokiaWildflyClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double totalWildflyTimeForQueries(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        return timeTakenByRestQueryApi(contextName, timeType) + timeTakenByRestQueryController(contextName, timeType)
                + timeTakenByRestQueryView(contextName, timeType);
    }


    public double totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        return timeTakenByRestCommandApi(contextName, timeType) + timeTakenByCommandController(contextName, timeType)
                + timeTakenByCommandHandler(contextName, timeType) + timeTakenByEventListenerAndProcessor(contextName, timeType);
    }
}


