package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.utils.WildflyJolokiaClient;

import javax.management.MalformedObjectNameException;

import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;

public class DefaultWildflyJmxService implements WildflyJmxService {
    private ExternalProperties props = ExternalProperties.getInstance();
    private WildflyJolokiaClient wildflyJolokiaClient = WildflyJolokiaClient.getInstance();


    public double timeTakenByCommandController(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(contextName)
                .append("*").toString();

        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByCommandHandler(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(contextName)
                .append("*").toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByEventListener(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("*").toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
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
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double totalWildflyTimeForQueries(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.value(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        return timeTakenByRestCommandApi(contextName, timeType) + timeTakenByCommandController(contextName, timeType)
                + timeTakenByCommandHandler(contextName, timeType) + timeTakenByEventListener(contextName, timeType);
    }
}


