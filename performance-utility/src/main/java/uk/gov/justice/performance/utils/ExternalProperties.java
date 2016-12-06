package uk.gov.justice.performance.utils;

import static uk.gov.justice.performance.utils.CommonConstant.DEFAULT_PROPERTIES_FILE;
import static uk.gov.justice.performance.utils.CommonConstant.OVERRIDES_FILE_PROPERTY_NAME;

import java.util.Properties;

public class ExternalProperties {
    private static ExternalProperties instance;
    private final Properties properties;

    private ExternalProperties() {
        properties = PropertiesFactory.safeLoadDefaultsAndOverrides(DEFAULT_PROPERTIES_FILE, System.getProperty(OVERRIDES_FILE_PROPERTY_NAME));
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

    public Properties getProperties() {
        return properties;
    }

}