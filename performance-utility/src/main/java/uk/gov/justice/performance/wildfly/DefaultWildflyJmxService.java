package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.performance.MBean;
import uk.gov.justice.performance.utils.CommonConstant;
import uk.gov.justice.performance.utils.WildflyJolokiaClient;

import javax.management.MalformedObjectNameException;
import java.util.Properties;

import static uk.gov.justice.performance.utils.CommonConstant.METRICS_NAME;

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

    public void setProps(Properties props) {
        this.props = props;
    }

    public void setWildflyJolokiaClient(WildflyJolokiaClient wildflyJolokiaClient) {
        this.wildflyJolokiaClient = wildflyJolokiaClient;
    }

    private double timeTakenByRestCommandApi(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mbeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-command-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        mBean.setName(mbeanName);
        return wildflyJolokiaClient.getJmxAttributeValue(mbeanName, timeType);
    }

    private double timeTakenByCommandController(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mbeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".controller.command-")
                .append(contextName)
                .append("*").toString();
        mBean.setName(mbeanName);
        return wildflyJolokiaClient.getJmxAttributeValue(mbeanName, timeType);
    }

    private double timeTakenByCommandHandler(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mbeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.queue.")
                .append(contextName)
                .append(".handler.command-")
                .append(contextName)
                .append("*").toString();
        mBean.setName(mbeanName);
        return wildflyJolokiaClient.getJmxAttributeValue(mbeanName, timeType);
    }

    private double timeTakenByEventListener(MBean mBean, String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mbeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.jms.topic.")
                .append(contextName)
                .append(".event-")
                .append(contextName)
                .append("*").toString();
        mBean.setName(mbeanName);
        return wildflyJolokiaClient.getJmxAttributeValue(mbeanName, timeType);
    }


    public MBean totalWildflyTimeForQueries(String contextName, String timeType)
            throws J4pException, MalformedObjectNameException {
        String mBeanName = new StringBuilder().append(props.getProperty(METRICS_NAME))
                .append(":name=wildfly.rest.")
                .append(contextName)
                .append("-query-api/")
                .append(contextName)
                .append("/")
                .append("*").toString();
        MBean mbean = new MBean();
        mbean.setName(mBeanName);
        mbean.setTime(wildflyJolokiaClient.getJmxAttributeValue(mBeanName, timeType));
        return mbean;
    }

    public MBean totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException {
        MBean mBean = new MBean();
        double time = timeTakenByRestCommandApi(mBean, contextName, timeType) + timeTakenByCommandController(mBean, contextName, timeType)
                + timeTakenByCommandHandler(mBean, contextName, timeType) + timeTakenByEventListener(mBean, contextName, timeType);
        mBean.setTime(time);
        return mBean;
    }
}
