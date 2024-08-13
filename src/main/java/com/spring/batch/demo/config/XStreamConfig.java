package com.spring.batch.demo.config;

import com.spring.batch.demo.dto.DailyAggregatedSensorData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XStreamConfig {

    @Bean
    public static XStream getXStream() {
        XStream xStream = new XStream(new PureJavaReflectionProvider());
        xStream.allowTypes(new Class[]{DailyAggregatedSensorData.class});

        xStream.alias("daily-data", DailyAggregatedSensorData.class);
        xStream.alias("date", String.class);
        xStream.alias("min", Double.class);
        xStream.alias("avg", Double.class);
        xStream.alias("max", Double.class);

        return xStream;
    }
}
