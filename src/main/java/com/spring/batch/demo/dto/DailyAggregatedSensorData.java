package com.spring.batch.demo.dto;

import com.thoughtworks.xstream.security.ExplicitTypePermission;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

public record DailyAggregatedSensorData(String date, double min, double avg, double max) {

    public static final String ITEM_ROOT_ELEMENT_NAME = "daily-data";

    public static XStreamMarshaller getMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        Map<String, Class> aliases = new HashMap<>();

        aliases.put(ITEM_ROOT_ELEMENT_NAME, DailyAggregatedSensorData.class);
        aliases.put("date", String.class);
        aliases.put("min", Double.class);
        aliases.put("avg", Double.class);
        aliases.put("max", Double.class);

        ExplicitTypePermission typePermission = new ExplicitTypePermission(new Class[]{DailyAggregatedSensorData.class});

        marshaller.setAliases(aliases);
        marshaller.setTypePermissions(typePermission);

        return marshaller;
    }

}
