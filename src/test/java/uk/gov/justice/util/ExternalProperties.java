package uk.gov.justice.util;

import org.jolokia.client.J4pClient;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;


public class ExternalProperties {

    private static final String PROPERTY_FILE_NAME = "application.properties";
    private static final String WILDFLY_JOLOKIA_FULL_PATH = "wildfly.jolokia.full.path";
    private static final String ARTEMIS_JOLOKIA_FULL_PATH = "artemis.jolokia.full.path";
    private static final String METRICS_NAME = "metrics.name";
    private static final String CONTEXT_NAME = "context.name";
    private static final String COMMAND_EXPECTED_TIME_TAKEN = "command.expected.time.taken";
    private static final String QUERY_EXPECTED_TIME_TAKEN = "query.expected.time.taken";
    private static final String SPLITTER = ",";
    private java.util.Properties properties = new java.util.Properties();

    public ExternalProperties() {
        loadProperties();
    }

    public J4pClient getWildflyJ4PClient() {
        return new J4pClient(properties.getProperty(WILDFLY_JOLOKIA_FULL_PATH));
    }

    public J4pClient getArtemisJ4PClient() {
        return new J4pClient(properties.getProperty(ARTEMIS_JOLOKIA_FULL_PATH));
    }

    private void loadProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                fail("Error reading " + PROPERTY_FILE_NAME);
            }
        } else {
            fail(PROPERTY_FILE_NAME + "not found in the classpath");
        }
    }

    public String[] getContextNames() {
        return properties.getProperty(CONTEXT_NAME).split(SPLITTER);
    }

    public String getMetricsName() {
        return properties.getProperty(METRICS_NAME);
    }

    public double getExpectedTimeTakenByCommands() {
        return Double.parseDouble(properties.getProperty(COMMAND_EXPECTED_TIME_TAKEN));
    }

    public double getExpectedTimeTakenByQueries() {
        return Double.parseDouble(properties.getProperty(QUERY_EXPECTED_TIME_TAKEN));
    }
}