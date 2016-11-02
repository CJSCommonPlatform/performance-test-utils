package uk.gov.justice.performance.wildfly;


import org.jolokia.client.exception.J4pException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.justice.performance.utils.ExternalProperties;
import uk.gov.justice.performance.utils.JolokiaWildflyClient;

import javax.management.MalformedObjectNameException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWildflyJmxServiceTest {

    private static final String CONTEXT_NAME = "people";
    private static final String JMX_ATTRIBUTE_NAME = "Mean";

    @InjectMocks
    private DefaultWildflyJmxService defaultWildflyJmxService;

    @Mock
    private ExternalProperties props;

    @Mock
    private JolokiaWildflyClient jolokiaWildflyClient;


    @Test
    public void shouldReturnTimeTakenByCommandController() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByCommandController(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByCommandHandler() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByCommandHandler("people", "Mean"), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByEventListenerAndProcessor() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByEventListener("people", "Mean"), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByRestCommandApi() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByRestCommandApi("people", "Mean"), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByRestQueryApi() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries("people", "Mean"), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturntotalWildflyTimeForCommands() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForCommands("people", "Mean"), lessThanOrEqualTo(4.0));
    }

    @Test
    public void shouldReturntotalWildflyTimeForQueries() throws MalformedObjectNameException, J4pException {
        when(props.value(anyString())).thenReturn("abc");
        when(jolokiaWildflyClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries("people", "Mean"), lessThanOrEqualTo(3.0));
    }
}
