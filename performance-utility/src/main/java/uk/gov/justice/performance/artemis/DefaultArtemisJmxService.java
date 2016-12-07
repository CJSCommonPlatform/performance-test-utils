package uk.gov.justice.performance.artemis;


import static uk.gov.justice.performance.utils.CommonConstant.ARTEMIS_JOLOKIA_URL_LIST;
import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;
import static uk.gov.justice.performance.utils.CommonConstant.PROXY_URL;

import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;

import uk.gov.justice.performance.utils.ArtemisJolokiaClient;

public class DefaultArtemisJmxService implements ArtemisJmxService {

    private Properties props;
    private ArtemisJolokiaClient artemisJolokiaClient;

    public DefaultArtemisJmxService(Properties props) {
        this.props = props;
        this.artemisJolokiaClient = new ArtemisJolokiaClient(this.props.getProperty(ARTEMIS_JOLOKIA_URL_LIST),this.props.getProperty(PROXY_URL));
    }
    
    public DefaultArtemisJmxService(Properties props, ArtemisJolokiaClient artemisJolokiaClient) {
        this.props = props;
        this.artemisJolokiaClient = artemisJolokiaClient;
    }

    public void setProps( Properties props ) {
        this.props = props;
    }

    public void setArtemisJolokiaClient(ArtemisJolokiaClient artemisJolokiaClient) {
        this.artemisJolokiaClient = artemisJolokiaClient;
    }

    public double timeMessageStaysInCommandQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".controller.command").toString();
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double timeMessageStaysInHandlerQueue(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".handler.command").toString();
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public double timeMessageStaysInEventListenerTopic(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
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
