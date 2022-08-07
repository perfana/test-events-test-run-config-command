# test-events-test-run-config-command

Run a command to capture test-run configuration data and broadcast this on the event-bus. 

Test-run config can be sent as key-value pair or as json structure.

Json can contain include and exclude fields to indicate what to compare and what to ignore to be stored and in the compare.

Tags are used to specify the type of configuration data (e.g. GitHub or k8s).

## use

```xml
<plugins>
    <plugin>
        <groupId>io.perfana</groupId>
        <artifactId>event-scheduler-maven-plugin</artifactId>
        <configuration>
            <eventSchedulerConfig>
                <debugEnabled>true</debugEnabled>
                <schedulerEnabled>true</schedulerEnabled>
                <failOnError>true</failOnError>
                <continueOnEventCheckFailure>true</continueOnEventCheckFailure>
                <eventScheduleScript>
                </eventScheduleScript>
                <testConfig>
                    <systemUnderTest>${systemUnderTest}</systemUnderTest>
                    <version>${version}</version>
                    <workload>${workload}</workload>
                    <testEnvironment>${testEnvironment}</testEnvironment>
                    <testRunId>${testRunId}</testRunId>
                    <buildResultsUrl>${buildResultsUrl}</buildResultsUrl>
                    <rampupTimeInSeconds>${rampupTimeInSeconds}</rampupTimeInSeconds>
                    <constantLoadTimeInSeconds>${constantLoadTimeInSeconds}</constantLoadTimeInSeconds>
                    <annotations>${annotations}</annotations>
                    <tags>${tags}</tags>
                </testConfig>
                <eventConfigs>
                    <eventConfig implementation="io.perfana.events.testrunconfigcommand.event.TestRunConfigCommandEventConfig">
                        <name>GitGetHash</name>
                        <command>git rev-parse --verify HEAD</command>
                        <output>key</output>
                        <key>https://github.com/perfana/perfana-gatling-afterburner</key>
                        <tags>GitHub,optimus-prime-be</tags>
                    </eventConfig>
                    <eventConfig implementation="io.perfana.events.testrunconfigcommand.event.TestRunConfigCommandEventConfig">
                        <name>KubernetesGetDeployment</name>
                        <command>kubectl get deployment -n acme -o=json optimus-prime-be-afterburner</command>
                        <include>env,resources,image,replicas,strategy,kubernetes</include>
                        <exclude>status</exclude>
                        <output>json</output>
                        <tags>k8s,optimus-prime-be</tags>
                    </eventConfig>
                </eventConfigs>
            </eventSchedulerConfig>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>io.perfana</groupId>
                <artifactId>test-events-test-run-config-command</artifactId>
                <version>${test-events-test-run-config-command.version}</version>
            </dependency>
            <dependency>
                <groupId>io.perfana</groupId>
                <artifactId>perfana-java-client</artifactId>
                <version>${perfana-java-client.version}</version>
            </dependency>
        </dependencies>
    </plugin>
</plugins>
```

See also:
* https://github.com/perfana/event-scheduler-maven-plugin
* https://github.com/perfana/event-scheduler
* https://github.com/perfana/perfana-java-client
