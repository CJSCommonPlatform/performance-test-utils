package uk.gov.justice.performance.wildfly;


import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;

import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;

import uk.gov.justice.performance.utils.CommonConstant;
import uk.gov.justice.performance.utils.WildflyJolokiaClient;

public class DefaultWildflyJmxService implements WildflyJmxService {
    private Properties props;
    private WildflyJolokiaClient wildflyJolokiaClient;

    public DefaultWildflyJmxService(Properties props) {
        this.props = props;
        this.wildflyJolokiaClient = new WildflyJolokiaClient(this.props.getProperty(CommonConstant.WILDFLY_JOLOKIA_URL_LIST),this.props.getProperty(CommonConstant.PROXY_URL));
    }

    public DefaultWildflyJmxService(Properties props, WildflyJolokiaClient wildflyJolokiaClient) {
        this.props = props;
        this.wildflyJolokiaClient = wildflyJolokiaClient;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public void setWildflyJolokiaClient(WildflyJolokiaClient wildflyJolokiaClient) {
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
}
