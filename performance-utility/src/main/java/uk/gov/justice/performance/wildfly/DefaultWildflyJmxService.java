package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.utils.CommonConstant;
import uk.gov.justice.performance.utils.WildflyJolokiaClient;

import javax.management.MalformedObjectNameException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;
import static uk.gov.justice.performance.utils.CommonConstant.SPECIFIC_COMMAND_RELATED_EVENTS;

public class DefaultWildflyJmxService implements WildflyJmxService {
    private Properties props;
    private WildflyJolokiaClient wildflyJolokiaClient;

    public DefaultWildflyJmxService(Properties props) {
        this.props = props;
        this.wildflyJolokiaClient = new WildflyJolokiaClient(this.props.getProperty(CommonConstant.WILDFLY_JOLOKIA_URL_LIST), this.props.getProperty(CommonConstant.PROXY_URL));
    }

    public DefaultWildflyJmxService(Properties props, WildflyJolokiaClient wildflyJolokiaClient) {
        this.props = props;
        this.wildflyJolokiaClient = wildflyJolokiaClient;
    }

    public double timeTakenByCommandController(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(contextName)
                .append("*").toString();

        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByCommandHandler(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(contextName)
                .append("*").toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByEventListener(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("*").toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenByRestCommandApi(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
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
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
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

    public double timeTakenBySpecificCommandInCommandController(String contextName, String commandName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(commandName).toString();

        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenBySpecificCommandInCommandHandler(String contextName, String commandName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(commandName).toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double timeTakenBySpecificCommandInEventListener(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        List<Double> eventValues = new ArrayList<>();
        for (String event : props.getProperty(SPECIFIC_COMMAND_RELATED_EVENTS).split(COMMA)) {
            String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                    .append(":name=wildfly.jms.topic.")
                    .append(contextName)
                    .append(".event-")
                    .append(event).toString();
            eventValues.add(wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType));
        }
        return Collections.max(eventValues);
    }

    public double timeTakenBySpecificCommandInRestCommandApi(String restEndPoint, String timeType)
            throws J4pException, MalformedObjectNameException {
        String searchStr = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(restEndPoint).toString();
        return wildflyJolokiaClient.getJmxAttributeValue(searchStr, timeType);
    }

    public double totalWildflyTimeForSpecificCommand(String contextName, String commandName, String restEndPoint, String timeType) throws J4pException, MalformedObjectNameException {
        return timeTakenBySpecificCommandInRestCommandApi(restEndPoint, timeType) + timeTakenBySpecificCommandInCommandController(contextName, commandName, timeType)
                + timeTakenBySpecificCommandInCommandHandler(contextName, commandName, timeType) + timeTakenBySpecificCommandInEventListener(contextName, timeType);
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public void setWildflyJolokiaClient(WildflyJolokiaClient wildflyJolokiaClient) {
        this.wildflyJolokiaClient = wildflyJolokiaClient;
    }
}
