package uk.gov.justice.performance.artemis;


import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.utils.JolokiaArtemisClient;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.management.MalformedObjectNameException;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultArtemisJmxServiceTest {
    private static final String CONTEXT_NAME = "people";
    private static final String JMX_ATTRIBUTE_NAME = "Mean";

    @InjectMocks
    private DefaultArtemisJmxService defaultArtemisJmxService;

    @Mock
    private ExternalProperties props;

    @Mock
    private JolokiaArtemisClient jolokiaArtemisClient;

    @Test
    public void shouldReturnTimeMessageStaysInCommandQueue() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaArtemisClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultArtemisJmxService.timeMessageStaysInCommandQueue(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeMessageStaysInHandlerQueue() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaArtemisClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultArtemisJmxService.timeMessageStaysInHandlerQueue(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeMessageStaysInEventListenerTopic() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaArtemisClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultArtemisJmxService.timeMessageStaysInEventListenerTopic(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTotalTimeMessageStaysInQueuesAndTopic() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaArtemisClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultArtemisJmxService.totalTimeMessageStaysInQueuesAndTopic(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(3.0));
    }
}
