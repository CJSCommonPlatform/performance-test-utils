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

public class JolokiaArtemisClient {
    private static JolokiaArtemisClient instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(JolokiaArtemisClient.class);
    private static final String ARTEMIS_JOLOKIA_FULL_PATH = "artemis.jolokia.full.path";
    private ExternalProperties props = ExternalProperties.getInstance();
    private J4pClient j4pClient = new J4pClient(props.value(ARTEMIS_JOLOKIA_FULL_PATH));
    private static final double ZERO = 0.0;

    /**
     * Singleton
     *
     * @return instance of the class
     */
    public static JolokiaArtemisClient getInstance() {
        if (instance == null) {
            instance = new JolokiaArtemisClient();
        }
        return instance;
    }

    public double getJmxAttributeValue(String mBeanName, String timeType) {
        try {
            J4pReadRequest req = new J4pReadRequest(mBeanName);
            J4pReadResponse resp = j4pClient.execute(req);
            return resp.getValue(timeType);
        } catch (Exception ex) {
            LOGGER.error("mBean not found {}", mBeanName);
            return ZERO;
        }
    }
}
