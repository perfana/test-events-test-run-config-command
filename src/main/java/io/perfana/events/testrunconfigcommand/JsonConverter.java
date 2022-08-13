package io.perfana.events.testrunconfigcommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import io.perfana.eventscheduler.exception.EventSchedulerRuntimeException;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonConverter {

    private static final JsonMapper jsonMapper = JsonMapper.builder().build();

    private static final Pattern CONTAINER_ENV_PATTERN = Pattern.compile("spec.template.spec.containers\\.\\d+\\.env");

    private JsonConverter() {
        // only statics: hide constructor
    }

    /**
     * Flatten a json structure to flat key-value pairs.
     *
     * Based on <a href="https://stackoverflow.com/questions/20355261/how-to-deserialize-json-into-flat-map-like-structure#answer-24150263">...</a>
     *
     * Has some knowledge about kubernetes json: turns container env variables (name,value pairs) into keys that con
     * contain the name.
     *
     * Example: spec.template.spec.containers.0.env.1.JDK_JAVA_OPTIONS=-javaagent:/super-agent.jar
     *
     * @param json a json text
     */
    public static Map<String, String> flatten(String json) {
        Map<String, String> map = new HashMap<>();
        try {
            addKeys("", jsonMapper.readTree(json), map);
        } catch (IOException e) {
            throw new EventSchedulerRuntimeException("failed to parse json", e);
        }
        return map;
    }

    /**
     * Return flattened list of key-value pairs, with includes and excludes applied, it that order.
     *
     * If includes is empty, the result will be empty.
     *
     * @param json a json text
     * @param includes if part of a flattened key, the item is included
     * @param excludes if part of a flattened key, the item is excluded (after includes are applied)
     * @return map of key-value pairs, flattened and filtered
     */
    public static Map<String, String> flatten(String json, List<String> includes, List<String> excludes) {
        if (includes.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> map = flatten(json);
        Map<String, String> inclMap = map.entrySet().stream()
                .filter(e -> isMentionedIn(e.getKey(), includes))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return inclMap.entrySet().stream()
                .filter(e -> !isMentionedIn(e.getKey(), excludes))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, String> flatten(String json, String includesAsCommaSepList, String excludesAsCommaSepList) {
        return flatten(json, commaSepToList(includesAsCommaSepList), commaSepToList(excludesAsCommaSepList));
    }

    private static boolean isMentionedIn(String key, List<String> listOfKeyParts) {
        return listOfKeyParts.stream().anyMatch(key::contains);
    }

    private static void addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map) {
        if (jsonNode.isObject()) {
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";
            addFieldsOfObject(pathPrefix, (ObjectNode) jsonNode, map);
        } else if (jsonNode.isArray()) {
            if (CONTAINER_ENV_PATTERN.matcher(currentPath).matches()) {
                addNameValuePairs(currentPath, (ArrayNode) jsonNode, map);
            }
            else {
                addKeysOfArray(currentPath, (ArrayNode) jsonNode, map);
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }

    private static void addKeysOfArray(String pathPrefix, ArrayNode jsonNode, Map<String, String> map) {
        for (int i = 0; i < jsonNode.size(); i++) {
            addKeys(pathPrefix + "." + i, jsonNode.get(i), map);
        }
    }

    private static void addFieldsOfObject(String pathPrefix, ObjectNode jsonNode, Map<String, String> map) {
        Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();
        while (iter.hasNext()) {
            Map.Entry<String, JsonNode> entry = iter.next();
            addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
        }
    }

    private static void addNameValuePairs(String pathPrefix, ArrayNode jsonNode, Map<String, String> map) {
        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode propNode = jsonNode.get(i);
            String value = getK8sPropValue(propNode);
            map.put(pathPrefix + "." + i + "." + propNode.get("name").asText(), value);
        }
    }

    private static String getK8sPropValue(JsonNode propNode) {
        JsonNode node = propNode.get("value");
        if (node == null) {
            node = propNode.get("valueFrom");
            if (node != null) {
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                String field = fields.hasNext() ? fields.next().getKey() : "unkown";
                return "[valueFrom:" + field + "]";
            }
        }
        if (node == null) {
            return "[no value or valueFrom found]";
        }
        return node.textValue();
    }

    static List<String> commaSepToList(String commaSepList) {
        if (commaSepList == null || commaSepList.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(commaSepList.split(",")).collect(Collectors.toList());
    }
}
