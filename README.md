**Performance Test Utils**

A utility that reads the jmx values from Artemis and Wildfly Metrics' and assert the values with user defined expected values.

**Description:**

Metrics' are exposed via JMX in the domain: uk.gov.justice.metrics

The utility reads the values and assert them against user defined values.

**Usage:**

The utility is tested on local vagrant box with jolokia. The jolokia ports for vagrant are:

Artemis: 8161
Wildfly: 9080

There are two instances of Artemis on DEV. The URLs are:
http://***REMOVED***:8161/jolokia

http://***REMOVED***:8161/jolokia

The utility connects to DEV via Proxy which is:
http://***REMOVED***:3128

The utility returns the average values of all the instances. The integration tests show how to use the utility.