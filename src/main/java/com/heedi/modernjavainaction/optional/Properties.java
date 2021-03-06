package com.heedi.modernjavainaction.optional;

import java.util.HashMap;
import java.util.Map;

public class Properties {
    private Map<String, String> property;

    public Properties() {
        this.property = new HashMap<>();
    }

    public void setProperty(String key, String value) {
        this.property.put(key, value);
    }

    public String getProperty(String name) {
        return this.property.get(name);
    }
}
