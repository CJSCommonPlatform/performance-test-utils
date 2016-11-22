package uk.gov.justice.performance.utils;


import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.DEV_PROXY_FULL_PATH;
import static uk.gov.justice.performance.utils.CommonConstant.ZERO;

public class ArtemisJolokiaClient {
    private static ArtemisJolokiaClient instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisJolokiaClient.class);
    private static final String ARTEMIS_JOLOKIA_FULL_PATHS = "artemis.jolokia.full.path";
    private static ExternalProperties props = ExternalProperties.getInstance();
    private static List<J4pClient> j4pClients;

    /**
     * Singleton
     *
     * @return instance of the class
     */
    public static ArtemisJolokiaClient getInstance() {
        if (instance == null) {
            initialiseClients();
            instance = new ArtemisJolokiaClient();
        }
        return instance;
    }

    /**
     * This method returns the average time of all the nodes.
     */
    public double getJmxAttributeValue(String mBeanName, String timeType) {
        double result = ZERO;
        for (J4pClient j4pClient : j4pClients) {
            try {
                J4pReadRequest req = new J4pReadRequest(mBeanName);
                J4pReadResponse resp = j4pClient.execute(req);
                result = result + ((Number) resp.getValue(timeType)).doubleValue();
            } catch (Exception ex) {
                LOGGER.error("mBean not found {}, {}", mBeanName, ex);
            }
        }

        if (result == ZERO) {
            return result;
        }
        return result / j4pClients.size();
    }

    /*
    *This method creates multiple clients for all the Artemis nodes.
    * */
    private static void initialiseClients() {
        j4pClients = new ArrayList<J4pClient>();
        for (String url : props.value(ARTEMIS_JOLOKIA_FULL_PATHS).split(COMMA)) {
            j4pClients.add(J4pClient.url(url).proxy(props.value(DEV_PROXY_FULL_PATH)).build());
        }
    }
}
