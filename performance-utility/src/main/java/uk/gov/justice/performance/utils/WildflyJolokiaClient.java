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
import java.util.ArrayList;
import java.util.List;

public class WildflyJolokiaClient {
    private static WildflyJolokiaClient instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(WildflyJolokiaClient.class);
    private static final String WILDFLY_JOLOKIA_FULL_PATH = "wildfly.jolokia.full.path";
    private static ExternalProperties props = ExternalProperties.getInstance();
    private static List<J4pClient> j4pClients;
    private static final double ZERO = 0.0;

    /**
     * Singleton
     *
     * @return instance of the class
     */
    public static WildflyJolokiaClient getInstance() {
        if (instance == null) {
            initialiseClients();
            instance = new WildflyJolokiaClient();
        }
        return instance;
    }

    /**
     * This method returns the average time of all the nodes.
     */
    public double getJmxAttributeValue(String searchStr, String timeType) throws MalformedObjectNameException, J4pException {
        double totalTime = ZERO;
        for (J4pClient j4pClient : j4pClients) {
            J4pSearchRequest searchRequest = new J4pSearchRequest(searchStr);
            J4pSearchResponse searchResponse = j4pClient.execute(searchRequest);
            totalTime = totalTime + getTimeValue(searchResponse.getMBeanNames(), timeType, j4pClient);
        }
        return totalTime;
    }

    private double getTimeValue(List<String> mBeanNames, String timeType, J4pClient j4pClient)
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

    /*
    *This method creates multiple clients for all the Artemis nodes.
    * */
    private static void initialiseClients() {
        j4pClients = new ArrayList<J4pClient>();
        for (String url : props.value(WILDFLY_JOLOKIA_FULL_PATH).split(",")) {
//            j4pClients.add(J4pClient.url(url).proxy(props.value(DEV_PROXY_FULL_PATHS)).build());
            j4pClients.add(J4pClient.url(url).build());
        }
    }
}