package edu.depaul.cdm.se452.rfa.roomate.service;

import java.util.Iterator;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonParsingService {
    /**
     * The {@code JsonParsingService} class provides methods to parse JSON objects
     * and extract integer values.
     *
     * <p>This class currently contains one public method:
     * <ul>
     * <li>{@link #parseJsonForIntegers(String)} - Parses a JSON string to find and
     * return a map of all the integer values contained within the JSON object.</li>
     * </ul>
     * </p>
     *
     * <pre>
     * Example usage:
     * {@code
     * JsonParsingService service = new JsonParsingService();
     * String jsonResponse = "{\"key1\": 1, \"key2\": 2, \"key3\": \"value\"}";
     * Map<String, Integer> integers = service.parseJsonForIntegers(jsonResponse);
     * System.out.println(integers); // Output: {key1=1, key2=2}
     * }
     * </pre>
     */
    public Map<String, Integer> parseJsonForIntegers(String jsonResponse) {
        Map<String, Integer> weightValues = new HashMap<>();

        // parse the JSON response
        JSONObject jsonObject = new JSONObject(jsonResponse);

        // iterate through the keys in the JSON object
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof Integer) {
                weightValues.put(key, jsonObject.getInt(key));
            }
        }

        return integerValues;
    }
}