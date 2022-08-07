/*
 * Copyright (C) 2020-2022 Peter Paul Bakker - Perfana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.perfana.events.testrunconfigcommand;

import io.perfana.eventscheduler.EventMessageBusSimple;
import io.perfana.eventscheduler.api.config.TestConfig;
import io.perfana.eventscheduler.api.message.EventMessageBus;
import io.perfana.eventscheduler.log.EventLoggerStdOut;
import org.junit.jupiter.api.Test;

class TestRunConfigCommandEventTest {

    @Test
    void beforeTestKeyValue() {
        TestRunConfigCommandEventConfig eventConfig = new TestRunConfigCommandEventConfig();
        eventConfig.setEventFactory(TestRunConfigCommandEventFactory.class.getSimpleName());
        eventConfig.setName("GitGetHash");
        eventConfig.setEnabled(true);
        eventConfig.setTestConfig(TestConfig.builder().build());
        eventConfig.setCommand("git rev-parse --verify HEAD");
        eventConfig.setOutput("key");
        eventConfig.setKey("https://github.com/perfana/perfana-gatling-afterburner");
        eventConfig.setTags("GitHub,optimus-prime-be");

        EventMessageBus messageBus = new EventMessageBusSimple();

        TestRunConfigCommandEvent event = new TestRunConfigCommandEvent(eventConfig.toContext(), messageBus, EventLoggerStdOut.INSTANCE);
        event.beforeTest();
        event.keepAlive();
        shortSleep();
        event.abortTest();
        event.afterTest();

        // not much to assert really... just look at System.out and
        // check it does not blow with an Exception...

    }

    @Test
    void beforeTestJson() {
        TestRunConfigCommandEventConfig eventConfig = new TestRunConfigCommandEventConfig();
        eventConfig.setEventFactory(TestRunConfigCommandEventFactory.class.getSimpleName());
        eventConfig.setName("KubernetesGetDeployment");
        eventConfig.setEnabled(true);
        eventConfig.setTestConfig(TestConfig.builder().build());
        //eventConfig.setCommand("kubectl get deployment -n acme -o=json optimus-prime-be-afterburner");
        eventConfig.setCommand("echo { \"test\": 123 }");
        eventConfig.setOutput("json");
        eventConfig.setInclude("env,resources,image,replicas,strategy,kubernetes");
        eventConfig.setExclude("status");
        eventConfig.setTags("k8s,optimus-prime-be");

        EventMessageBus messageBus = new EventMessageBusSimple();

        TestRunConfigCommandEvent event = new TestRunConfigCommandEvent(eventConfig.toContext(), messageBus, EventLoggerStdOut.INSTANCE);
        event.beforeTest();
        event.keepAlive();
        shortSleep();
        event.abortTest();
        event.afterTest();

        // not much to assert really... just look at System.out and
        // check it does not blow with an Exception...

    }

    private void shortSleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
