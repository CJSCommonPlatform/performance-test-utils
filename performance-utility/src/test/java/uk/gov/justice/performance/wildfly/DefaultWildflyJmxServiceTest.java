package uk.gov.justice.performance.wildfly;


import static org.hamcrest.MatcherAssert.assertThat;
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

import uk.gov.justice.performance.utils.WildflyJolokiaClient;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWildflyJmxServiceTest {

    private static final String CONTEXT_NAME = "people";
    private static final String JMX_ATTRIBUTE_NAME = "Mean";

    @InjectMocks
    private DefaultWildflyJmxService defaultWildflyJmxService;

    @Mock
    private Properties props;

    @Mock
    private WildflyJolokiaClient wildflyJolokiaClient;


    @Test
    public void shouldReturnTimeTakenByCommandController() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByCommandController(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByCommandHandler() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByCommandHandler(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByEventListenerAndProcessor() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByEventListener(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByRestCommandApi() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.timeTakenByRestCommandApi(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTimeTakenByRestQueryApi() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(1.0));
    }

    @Test
    public void shouldReturnTotalWildflyTimeForCommands() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForCommands(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(4.0));
    }

    @Test
    public void shouldReturnTotalWildflyTimeForQueries() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn("abc");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(1.0);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(3.0));
    }
}
