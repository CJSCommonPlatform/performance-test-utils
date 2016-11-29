package uk.gov.justice.performance.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.fail;
import static uk.gov.justice.performance.utils.CommonConstant.*;

public class ExternalProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalProperties.class);


    private static ExternalProperties instance;
    private final Properties properties = new Properties();

    private ExternalProperties() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);

        if (is != null) {
            try {
                properties.load(is);
                LOGGER.info("Contexts: {}, Command time expected: {}, Query time expected: {}", properties.getProperty(CONTEXT_NAMES),
                        properties.getProperty(COMMAND_EXPECTED_TIME_TAKEN), properties.getProperty(QUERY_EXPECTED_TIME_TAKEN));
            } catch (IOException e) {
                fail("error reading " + PROPERTY_FILE_NAME);
            }
        } else {
            fail(PROPERTY_FILE_NAME + " not found in the classpath");
        }
    }

    /**
     * Singleton
     *
     * @return instance of the class
     */
    public static ExternalProperties getInstance() {
        if (instance == null) {
            instance = new ExternalProperties();
        }
        return instance;
    }

    /**
     * Accessor method to read property values
     *
     * @param propertyName - name of the property defined in the properties files
     * @return value of the property
     */
    public String value(String propertyName) {
        return properties.getProperty(propertyName);
    }

}