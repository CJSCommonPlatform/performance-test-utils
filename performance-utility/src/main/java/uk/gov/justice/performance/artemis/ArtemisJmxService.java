package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.MBean;

import javax.management.MalformedObjectNameException;

public interface ArtemisJmxService {

    MBean totalTimeMessageStaysInQueuesAndTopic(String contextName, String timeType) throws J4pException, MalformedObjectNameException;
}
