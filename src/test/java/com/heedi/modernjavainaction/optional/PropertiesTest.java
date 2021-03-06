package com.heedi.modernjavainaction.optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesTest {

    @DisplayName("프로퍼티-지속시간을 읽는 로직 테스트")
    @Test
    void properties_test() {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        assertEquals(5, readDuration(props, "a"));
        assertEquals(0, readDuration(props, "b"));
        assertEquals(0, readDuration(props, "c"));
        assertEquals(0, readDuration(props, "d"));

        assertEquals(5, readDurationWithOptional(props, "a"));
        assertEquals(0, readDurationWithOptional(props, "b"));
        assertEquals(0, readDurationWithOptional(props, "c"));
        assertEquals(0, readDurationWithOptional(props, "d"));
    }

    private int readDuration(Properties props, String name) {
        String value = props.getProperty(name);
        if(value != null) {
            try {
                int i = Integer.parseInt(value);
                if(i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) {}
        }
        return 0;
    }

    private int readDurationWithOptional(Properties props, String name) {
        String value = props.getProperty(name);
        return OptionalUtil.stringToInt(value)
                .filter(i -> i > 0)
                .orElse(0);
    }
}