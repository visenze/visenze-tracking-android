package com.visenze.datatracking.data;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class EventTest {

    @Test
    public void toMap() {
        Event event = Event.createClickEvent();

        // long
        event.setTimestamp(1598973779000L);

        String category = "abc";
        event.setCategory(category);

        event.setValue("3.25");
        event.setPrice(3.24);

        // int
        event.setPosition(3);

        // big decimal
        event.setN1(new BigDecimal("212"));
        event.setN2(BigDecimal.valueOf(565.4));

        Map<String, String> map = event.toMap();

        assertEquals(category, map.get("cat"));
        assertEquals("1598973779000", map.get("ts"));
        assertEquals("3.24" , map.get("price"));
        assertEquals("3.25" , map.get("value"));
        assertEquals("212", map.get("n1"));
        assertEquals("565.4", map.get("n2"));
        assertEquals("3", map.get("pos"));

    }
}