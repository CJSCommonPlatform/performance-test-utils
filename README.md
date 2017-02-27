**Performance Test Utils**

A utility that reads the jmx values from Artemis and Wildfly Metrics' and assert the values with user defined expected values.

**Description:**

Metrics' are exposed via JMX in the domain: uk.gov.justice.metrics

The utility reads the values and assert them against user defined values.

**Usage:**

The utility is tested on local vagrant box with jolokia. The jolokia ports for vagrant are:

Artemis: 8161
Wildfly: 9080

The utility returns the average values of all the instances. The integration tests show how to use the utility.

Artemis, Wildfly and Proxy URLS are externalised so these should be passed as part of maven command e.g.

`mvn clean install -DartemisUrl=http://ip1:port/jolokia,http://ip2:port/jolokia -DwildflyUrl=http://ip1:port/jolokia,http://ip2:port/jolokia -DproxyUrl=http://ip:port -DcommandTime=10000 -DqueryTime=10000 -DcontextList=contextA,contextB
-Dspecific.command.name=test-command -Dspecific.command.related.events=event1,event2 -Dspecific.command.expected.time.taken=10000 -Dspecific.command.rest.end.point=10000`

All the parameters are mandatory except Proxy which is optional.