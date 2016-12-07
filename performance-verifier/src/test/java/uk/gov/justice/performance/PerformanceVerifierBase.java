package uk.gov.justice.performance;

import static uk.gov.justice.performance.utils.CommonConstant.DEFAULT_PROPERTIES_FILE;
import static uk.gov.justice.performance.utils.CommonConstant.OVERRIDES_FILE_PROPERTY_NAME;

import java.util.Properties;

import org.junit.BeforeClass;

import uk.gov.justice.performance.artemis.ArtemisJmxService;
import uk.gov.justice.performance.artemis.DefaultArtemisJmxService;
import uk.gov.justice.performance.utils.PropertiesFactory;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

public class PerformanceVerifierBase {
    protected static Properties props;
    protected static ArtemisJmxService artemisJmxService;
    protected static WildflyJmxService wildflyJmxService;

    @BeforeClass
    public static void setup() {
        props = PropertiesFactory.safeLoadDefaultsAndOverrides('/' + DEFAULT_PROPERTIES_FILE, System.getProperty(OVERRIDES_FILE_PROPERTY_NAME,""));
        artemisJmxService = new DefaultArtemisJmxService(props);
        wildflyJmxService = new DefaultWildflyJmxService(props);
    }
}
