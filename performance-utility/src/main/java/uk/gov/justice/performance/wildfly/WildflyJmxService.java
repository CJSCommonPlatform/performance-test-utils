package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;

import javax.management.MalformedObjectNameException;

public interface WildflyJmxService {

    double timeTakenByCommandController(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByCommandHandler(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByEventListener(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByRestCommandApi(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double totalWildflyTimeForQueries(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double totalWildflyTimeForSpecificCommand(String contextName, String commandName, String restEndPoint, String timeType)
            throws J4pException, MalformedObjectNameException;
}
