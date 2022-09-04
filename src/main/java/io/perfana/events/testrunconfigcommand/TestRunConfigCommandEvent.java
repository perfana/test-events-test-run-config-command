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

import io.perfana.eventscheduler.api.EventAdapter;
import io.perfana.eventscheduler.api.EventLogger;
import io.perfana.eventscheduler.api.message.EventMessage;
import io.perfana.eventscheduler.api.message.EventMessageBus;
import io.perfana.eventscheduler.exception.EventSchedulerRuntimeException;
import io.perfana.eventscheduler.util.TestRunConfigUtil;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRunConfigCommandEvent extends EventAdapter<TestRunConfigCommandEventContext> {

    public static final Pattern REGEX_SPLIT_QUOTES = Pattern.compile("\"([^\"]*)\"|(\\S+)");

    public TestRunConfigCommandEvent(TestRunConfigCommandEventContext eventContext, EventMessageBus messageBus, EventLogger logger) {
        super(eventContext, messageBus, logger);
    }

    @Override
    public void beforeTest() {

        String pluginName = TestRunConfigCommandEvent.class.getSimpleName() + "-" + eventContext.getName();

        String command = eventContext.getCommand();

        logger.info("About to run [" + command + "] for [" + eventContext.getTestContext().getTestRunId() + "]");

        List<String> commandList = splitCommand(command);

        try {
            String commandOutput = new ProcessExecutor()
                    .command(commandList)
                    .readOutput(true)
                    .redirectError(new PrefixedRedirectOutput(eventContext.getName() + ": ", System.err))
                    .timeout(30, TimeUnit.SECONDS)
                    .exitValue(0)
                    .execute()
                    .outputUTF8();

            String output = eventContext.getOutput();
            if ("keys".equals(output)) {
                // flatten json, apply filters
                Map<String, String> flatJson = JsonConverter.flatten(commandOutput, eventContext.getIncludes(), eventContext.getExcludes());
                logger.info("About to send " + flatJson.size() + " flattened key-value pairs");
                logger.debug("flatJsonMap: " + flatJson);
                EventMessage message = TestRunConfigUtil.createTestRunConfigMessageKeys(
                        pluginName,
                        flatJson,
                        eventContext.getTags());
                eventMessageBus.send(message);
            } else {
                // output can be key or json here
                EventMessage message = TestRunConfigUtil.createTestRunConfigMessage(
                        pluginName,
                        eventContext.getKey(),
                        commandOutput,
                        output,
                        eventContext.getTags(),
                        eventContext.getExcludes(),
                        eventContext.getIncludes());
                eventMessageBus.send(message);
            }
        } catch (InvalidExitValueException e) {
            throw new EventSchedulerRuntimeException("Unexpected exit value.", e);
        } catch (IOException e) {
            throw new EventSchedulerRuntimeException("IO exception to run command: [" + command + "]", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EventSchedulerRuntimeException("Got interrupted during command [" + command + "]");
        } catch (TimeoutException e) {
            throw new EventSchedulerRuntimeException("Got timeout for command [" + command + "]", e);
        }

    }

    private List<String> splitCommand(String command) {
        // https://stackoverflow.com/questions/3366281/tokenizing-a-string-but-ignoring-delimiters-within-quotes
        Matcher m = REGEX_SPLIT_QUOTES.matcher(command);
        List<String> commandList = new ArrayList<>();
        while (m.find()) {
            if (m.group(1) != null) {
                // Quoted
                commandList.add(m.group(1));
            } else {
                // Plain
                commandList.add(m.group(2));
            }
        }
        return commandList;
    }
}
