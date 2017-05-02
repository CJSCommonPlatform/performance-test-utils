package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.MBean;
import uk.gov.justice.performance.utils.ArtemisJolokiaClient;

import javax.management.MalformedObjectNameException;
import java.util.Properties;

import static uk.gov.justice.performance.utils.CommonConstant.ARTEMIS_JOLOKIA_URL_LIST;
import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;
import static uk.gov.justice.performance.utils.CommonConstant.PROXY_URL;

public class DefaultArtemisJmxService implements ArtemisJmxService {
    private Properties props;
    private ArtemisJolokiaClient artemisJolokiaClient;

    public DefaultArtemisJmxService(Properties props) {
        this.props = props;
        this.artemisJolokiaClient = new ArtemisJolokiaClient(this.props.getProperty(ARTEMIS_JOLOKIA_URL_LIST), this.props.getProperty(PROXY_URL));
    }

    public DefaultArtemisJmxService(Properties props, ArtemisJolokiaClient artemisJolokiaClient) {
        this.props = props;
        this.artemisJolokiaClient = artemisJolokiaClient;
    }

    private double timeMessageStaysInCommandQueue(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".controller.command").toString();
        mBean.setName(mBeanName);
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    private double timeMessageStaysInHandlerQueue(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=jms.queue.")
                .append(contextName)
                .append(".handler.command").toString();
        mBean.setName(mBean.getName() + " || " + mBeanName);
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    private double timeMessageStaysInEventListenerTopic(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("\\.event\\.listener\\.")
                .append(contextName)
                .append("\\.event").toString();
        mBean.setName(mBean.getName() + " || " + mBeanName);
        return artemisJolokiaClient.getJmxAttributeValue(mBeanName, timeType);
    }

    public MBean totalTimeMessageStaysInQueuesAndTopic(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        MBean mbean = new MBean();

        double time = timeMessageStaysInCommandQueue(mbean, contextName, timeType)
                + timeMessageStaysInHandlerQueue(mbean, contextName, timeType)
                + timeMessageStaysInEventListenerTopic(mbean, contextName, timeType);
        mbean.setTime(time);
        return mbean;
    }

    public void setArtemisJolokiaClient(ArtemisJolokiaClient artemisJolokiaClient) {
        this.artemisJolokiaClient = artemisJolokiaClient;
    }

    public void setProps(Properties props) {
        this.props = props;
    }
}
