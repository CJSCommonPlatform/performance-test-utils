Performance Test Utils

A utility that reads the jmx values from Artemis and Wildfly Metrics and assert them with user defined expected values.

Description:

Metrics are exposed via JMX in the domain: uk.gov.justice.metrics

The utility reads the values and assert them against user defined values

Usage:

_mvn clean install_ 

The utility works with jolokia on local vagrant box. The jolokia ports for vagrant are:

Artemis: 8161
Wildfly: 9080