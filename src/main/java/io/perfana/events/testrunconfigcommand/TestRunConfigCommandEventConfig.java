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

import io.perfana.eventscheduler.api.config.EventConfig;
import io.perfana.eventscheduler.api.config.TestContext;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class TestRunConfigCommandEventConfig extends EventConfig {

    private String command;
    private String output;
    private String includes;
    private String excludes;
    private String key;
    private String tags;

    @Override
    public TestRunConfigCommandEventContext toContext() {
        return new TestRunConfigCommandEventContext(super.toContext(), command, output, includes, excludes, key, tags);
    }

    @Override
    public TestRunConfigCommandEventContext toContext(TestContext override) {
        return new TestRunConfigCommandEventContext(super.toContext(override), command, output, includes, excludes, key, tags);
    }

    @Override
    public String toString() {
        return "TestRunConfigCommandEventConfig{" +
                "command='" + command + '\'' +
                ", output='" + output + '\'' +
                ", include='" + includes + '\'' +
                ", exclude='" + excludes + '\'' +
                ", key='" + key + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
