package uk.gov.justice.util;

import org.jolokia.client.J4pClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.util.artemis.ArtemisJmxFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.fail;


public class ExternalProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtemisJmxFactory.class);
    private static final String PROPERTY_FILE_NAME = "application.properties";
    public static final String WILDFLY_JOLOKIA_FULL_PATH = "wildfly.jolokia.full.path";
    public static final String ARTEMIS_JOLOKIA_FULL_PATH = "artemis.jolokia.full.path";
    public static final String METRICS_NAME = "metrics.name";
    public static final String CONTEXT_NAME = "context.name";
    public static final String COMMAND_EXPECTED_TIME_TAKEN = "command.expected.time.taken";
    public static final String QUERY_EXPECTED_TIME_TAKEN = "query.expected.time.taken";
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

    public String getContextName() {
        return properties.getProperty(CONTEXT_NAME);
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