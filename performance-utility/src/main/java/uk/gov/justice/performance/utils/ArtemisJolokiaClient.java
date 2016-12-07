package uk.gov.justice.performance.utils;


import static uk.gov.justice.performance.utils.CommonConstant.COMMA;
import static uk.gov.justice.performance.utils.CommonConstant.ZERO;

import java.util.ArrayList;
import java.util.List;

import org.jolokia.client.J4pClient;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtemisJolokiaClient {
    
    private Logger LOGGER = LoggerFactory.getLogger(ArtemisJolokiaClient.class);
    private List<J4pClient> j4pClients;

    public ArtemisJolokiaClient(String clients, String proxy) {
        initialiseClients(clients, proxy);
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
    private void initialiseClients(String clients, String proxy) {
        j4pClients = new ArrayList<J4pClient>();
        for (String url : clients.split(COMMA)) {
            j4pClients.add(J4pClient.url(url).proxy(proxy).build());
        }
    }
}
