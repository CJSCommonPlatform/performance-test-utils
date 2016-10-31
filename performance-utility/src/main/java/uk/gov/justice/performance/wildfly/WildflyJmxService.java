package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;

import javax.management.MalformedObjectNameException;

public interface WildflyJmxService {

    double timeTakenByCommandController(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByCommandHandler(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByEventListenerAndProcessor(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByRestCommandApi(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByRestQueryApi(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByRestQueryController(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double timeTakenByRestQueryView(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double totalWildflyTimeForQueries(String contextName, String timeType) throws J4pException, MalformedObjectNameException;

    double totalWildflyTimeForCommands(String contextName, String timeType) throws J4pException, MalformedObjectNameException;
}
