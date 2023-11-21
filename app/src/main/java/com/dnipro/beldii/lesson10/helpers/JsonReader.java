package com.dnipro.beldii.lesson10.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonReader {
    InputStream jsonStream;
    StringBuilder jsonContent;

    public JsonReader(InputStream jsonResource) {
        this.jsonStream = jsonResource;
        jsonContent = new StringBuilder();
        Reader reader = new InputStreamReader(jsonStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.lines().forEach(line -> jsonContent.append(line));
    }

    public JSONObject getAsObject() {
        try {
            return new JSONObject(this.jsonContent.toString());
        } catch (JSONException e) {
            e.getStackTrace();
        }
        return null;
    }

    public JSONArray getAsArray() {
        try {
            return new JSONArray(this.jsonContent.toString());
        } catch (JSONException e) {
            e.getStackTrace();
        }
        return null;
    }

    public String getAsString() {
        return this.jsonContent.toString();
    }
}