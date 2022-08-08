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

import io.perfana.eventscheduler.api.config.EventContext;
import net.jcip.annotations.Immutable;

@Immutable
public class TestRunConfigCommandEventContext extends EventContext {

    private final String command;
    private final String output;
    private final String includes;
    private final String excludes;
    private final String key;
    private final String tags;

    protected TestRunConfigCommandEventContext(EventContext context, String command, String output, String includes, String excludes, String key, String tags) {
        super(context, TestRunConfigCommandEventFactory.class.getName(), false);
        this.command = command;
        this.output = output;
        this.includes = includes;
        this.excludes = excludes;
        this.key = key;
        this.tags = tags;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    public String getIncludes() {
        return includes;
    }

    public String getExcludes() {
        return excludes;
    }

    public String getKey() {
        return key;
    }

    public String getTags() {
        return tags;
    }
}
