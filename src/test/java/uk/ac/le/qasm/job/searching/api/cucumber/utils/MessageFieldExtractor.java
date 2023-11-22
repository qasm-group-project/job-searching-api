package uk.ac.le.qasm.job.searching.api.cucumber.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class MessageFieldExtractor {

    private MessageFieldExtractor() {
    }

    public static String getResponseFieldValue(JsonNode response, String dotSeparatedFields) {
        List<String> fields = List.of(dotSeparatedFields.trim().split("\\."));
        String fieldValues = null;

        JsonNode payload = response;
        for (int i = 0; i < fields.size() - 1; i++) {
            if (payload.isArray()) {
                payload = payload.get(Integer.parseInt(fields.get(i)));
            } else {
                payload = payload.get(fields.get(i));
            }
        }

        JsonNode value = null;
        if (payload != null) {
            if(isInteger(fields.get(fields.size() - 1))) {
                value = payload.get(Integer.parseInt(fields.get(fields.size() - 1)));
            } else {
                value = payload.get(fields.get(fields.size() - 1));
            }
        }
        if (value != null && value.isTextual()) {
            fieldValues = value.textValue();
        } else if (value != null && value.isNumber()) {
            fieldValues = String.valueOf(value.numberValue());
        } else if (value != null) {
            fieldValues = value.toString();
            fieldValues = fieldValues.equals("null") ? null : fieldValues;
        }

        return fieldValues;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
