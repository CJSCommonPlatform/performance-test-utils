package uk.gov.justice.performance.utils;


import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.ZERO;

import java.util.ArrayList;
import java.util.List;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.jolokia.client.request.J4pSearchRequest;
import org.jolokia.client.request.J4pSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildflyJolokiaClient {
    private Logger LOGGER = LoggerFactory.getLogger(WildflyJolokiaClient.class);
    private static List<J4pClient> j4pClients;

    public WildflyJolokiaClient(String clients, String proxy) {
        initialiseClients(clients, proxy);
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
                double result = resp.getValue(timeType);

                LOGGER.info("{}: {} of {} from {}", result, timeType, name, j4pClient.getUri());
                totalTime = totalTime + result;
            } catch (Exception ex) {
                LOGGER.error("mBean not found {}, {}", name, ex);
                return ZERO;
            }
        }
        return totalTime;
    }

    /*
    *This method creates multiple clients for all the Artemis nodes.
    * */
    private void initialiseClients(String clients, String proxy) {
        j4pClients = new ArrayList<J4pClient>();
        for (String url : clients.split(COMMA)) {
            j4pClients.add(J4pClient.url(url).proxy(proxy).build());
        }
    }
}