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
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
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
                    .timeout(1, TimeUnit.MICROSECONDS)
                    .exitValue(0)
                    .execute()
                    .outputUTF8();

            EventMessage message = EventMessage.builder()
                    .pluginName(pluginName)
                    .variable("message-type", "test-run-config")
                    .variable("output", eventContext.getOutput())
                    .variable("key", eventContext.getKey())
                    .variable("tags", eventContext.getTags())
                    .variable("exclude", eventContext.getExclude())
                    .variable("include", eventContext.getInclude())
                    .message(commandOutput).build();

            this.eventMessageBus.send(message);

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
