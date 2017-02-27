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
    private static final String TEST_COMMAND = "test-command";
    private static final String TEST_VAL = "test-value";
    private static final double EXPECTED_VALUE = 1.0;

    @InjectMocks
    private DefaultWildflyJmxService defaultWildflyJmxService;

    @Mock
    private Properties props;

    @Mock
    private WildflyJolokiaClient wildflyJolokiaClient;

    @Test
    public void shouldReturnTimeTakenByCommandController() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenByCommandController(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenByCommandHandler() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenByCommandHandler(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenBySpecificCommandEventListenerAndProcessor()
            throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL + ",another-test-value");
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE, 2.0);
        assertThat(defaultWildflyJmxService.timeTakenBySpecificCommandInEventListener(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(2.0));
    }

    @Test
    public void shouldReturnTimeTakenBySpecificCommandInRestCommandApi() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenBySpecificCommandInRestCommandApi("rest-end-point", JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenBySpecificCommandInCommandController() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenBySpecificCommandInCommandController(CONTEXT_NAME, TEST_COMMAND, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenBySpecificCommandInCommandHandler() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenBySpecificCommandInCommandHandler(CONTEXT_NAME, TEST_COMMAND, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenByEventListenerAndProcessor() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenByEventListener(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenByRestCommandApi() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.timeTakenByRestCommandApi(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTimeTakenByRestQueryApi() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(EXPECTED_VALUE));
    }

    @Test
    public void shouldReturnTotalWildflyTimeForCommands() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForCommands(CONTEXT_NAME, JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(4.0));
    }

    @Test
    public void shouldReturnTotalWildflyTimeForSpecificCommand() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForSpecificCommand(CONTEXT_NAME, TEST_COMMAND, "rest-end-point", JMX_ATTRIBUTE_NAME),
                lessThanOrEqualTo(4.0));
    }

    @Test
    public void shouldReturnTotalWildflyTimeForQueries() throws MalformedObjectNameException, J4pException {
        when(props.getProperty(anyString())).thenReturn(TEST_VAL);
        when(wildflyJolokiaClient.getJmxAttributeValue(anyString(), anyString())).thenReturn(EXPECTED_VALUE);
        assertThat(defaultWildflyJmxService.totalWildflyTimeForQueries(CONTEXT_NAME, JMX_ATTRIBUTE_NAME), lessThanOrEqualTo(3.0));
    }
}
