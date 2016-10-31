package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;

import javax.management.MalformedObjectNameException;

public interface ArtemisJmxService {

    double timeMessageStaysInCommandQueue(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeMessageStaysInHandlerQueue(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeMessageStaysInEventListenerTopic(String contextName, String timeType) throws J4pException, MalformedObjectNameException;
}
