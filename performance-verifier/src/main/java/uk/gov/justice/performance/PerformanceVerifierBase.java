package uk.gov.justice.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.performance.artemis.ArtemisJmxService;
import uk.gov.justice.performance.artemis.DefaultArtemisJmxService;
import uk.gov.justice.performance.utils.PropertiesFactory;
import uk.gov.justice.performance.wildfly.DefaultWildflyJmxService;
import uk.gov.justice.performance.wildfly.WildflyJmxService;

import java.util.Properties;

import static uk.gov.justice.performance.utils.CommonConstant.DEFAULT_PROPERTIES_FILE;
import static uk.gov.justice.performance.utils.CommonConstant.OVERRIDES_FILE_PROPERTY_NAME;

public class PerformanceVerifierBase {
    protected static Properties props;
    protected static ArtemisJmxService artemisJmxService;
    protected static WildflyJmxService wildflyJmxService;

    static {
        Logger logger = LoggerFactory.getLogger(PerformanceVerifierBase.class);
        props = PropertiesFactory.safeLoadDefaultsAndOverrides('/' + DEFAULT_PROPERTIES_FILE, System.getProperty(OVERRIDES_FILE_PROPERTY_NAME,""));
        logger.info("Overrides file: {}", System.getProperty(OVERRIDES_FILE_PROPERTY_NAME));
        artemisJmxService = new DefaultArtemisJmxService(props);
        wildflyJmxService = new DefaultWildflyJmxService(props);
    }
}
