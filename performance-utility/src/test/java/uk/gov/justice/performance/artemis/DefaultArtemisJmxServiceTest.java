package uk.gov.justice.performance.artemis;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Properties;

import javax.management.MalformedObjectNameException;

import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.justice.performance.MBean;
import uk.gov.justice.performance.utils.ArtemisJolokiaClient;

@RunWith(MockitoJUnitRunner.class)
public class DefaultArtemisJmxServiceTest {
    private static final String CONTEXT_NAME = "people";
    private static final String JMX_ATTRIBUTE_NAME = "Mean";

    @InjectMocks
    private DefaultArtemisJmxService defaultArtemisJmxService;

    @Mock
    private Properties props;

    @Mock
    private ArtemisJolokiaClient artemisJolokiaClient;

    @Test
    public void shouldReturnTotalTimeMessageStaysInQueuesAndTopic() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(artemisJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        MBean mbean = defaultArtemisJmxService.totalTimeMessageStaysInQueuesAndTopic(CONTEXT_NAME, JMX_ATTRIBUTE_NAME);
        assertThat(mbean.getTime(), lessThanOrEqualTo(3.0));

    }
}
