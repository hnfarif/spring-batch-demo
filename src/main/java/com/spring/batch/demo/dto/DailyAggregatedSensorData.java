package com.spring.batch.demo.dto;

import com.spring.batch.demo.config.RecordConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.ExplicitTypePermission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class DailyAggregatedSensorData {

    private final String date;
    private final double min;
    private final double avg;
    private final double max;

    public static final String ITEM_ROOT_ELEMENT_NAME = "daily-data";

    public static XStreamMarshaller getMarshaller() {
        XStreamMarshaller marshaller = new XStreamMarshaller();

        Map<String, Object> aliases = new HashMap<>();

        aliases.put(ITEM_ROOT_ELEMENT_NAME, DailyAggregatedSensorData.class);
        aliases.put("date", String.class);
        aliases.put("min", Double.class);
        aliases.put("avg", Double.class);
        aliases.put("max", Double.class);

        ExplicitTypePermission typePermission = new ExplicitTypePermission(new Class[]{DailyAggregatedSensorData.class});

        marshaller.setTypePermissions(typePermission);
        marshaller.setAliases(aliases);
        marshaller.setConverters(new RecordConverter(marshaller.getXStream()));

        return marshaller;
    }

}
