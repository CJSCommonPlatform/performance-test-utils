package uk.gov.justice.performance.utils;


import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MalformedObjectNameException;
import java.util.List;

public class JolokiaWildflyClient {
    private static JolokiaWildflyClient instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(JolokiaWildflyClient.class);
    private static final String WILDFLY_JOLOKIA_FULL_PATH = "wildfly.jolokia.full.path";
    private static ExternalProperties props = ExternalProperties.getInstance();
    private static J4pClient j4pClient = new J4pClient(props.value(WILDFLY_JOLOKIA_FULL_PATH));
    private static final double ZERO = 0.0;

    /**
     * Singleton
     *
     * @return instance of the class
     */
    public static JolokiaWildflyClient getInstance() {
        if (instance == null) {
            instance = new JolokiaWildflyClient();
        }
        return instance;
    }

    public double getJmxAttributeValue(String searchStr, String timeType)
            throws MalformedObjectNameException, J4pException {
        J4pSearchRequest searchRequest = new J4pSearchRequest(searchStr);
        J4pSearchResponse searchResponse = j4pClient.execute(searchRequest);
        return getTimeValue(searchResponse.getMBeanNames(), timeType);
    }

    public double getTimeValue(List<String> mBeanNames, String timeType)
            throws MalformedObjectNameException, J4pException {
        double totalTime = ZERO;
        for (String name : mBeanNames) {
            try {
                J4pReadRequest req = new J4pReadRequest(name);
                J4pReadResponse resp = j4pClient.execute(req);
                totalTime = totalTime + (Double) resp.getValue(timeType);
            } catch (Exception ex) {
                LOGGER.error("mBean not found {}", name);
                return ZERO;
            }
        }
        return totalTime;
    }
}
