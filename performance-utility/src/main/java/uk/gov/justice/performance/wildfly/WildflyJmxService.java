package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import uk.gov.justice.performance.MBean;

import javax.management.MalformedObjectNameException;

public interface WildflyJmxService {

    MBean totalWildflyTimeForQueries(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    MBean totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException;
}
