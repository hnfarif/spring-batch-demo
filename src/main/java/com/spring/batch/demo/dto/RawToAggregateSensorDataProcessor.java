package com.spring.batch.demo.dto;

import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

public class RawToAggregateSensorDataProcessor implements ItemProcessor<DailySensorData, DailyAggregatedSensorData> {

    @Override
    public DailyAggregatedSensorData process(DailySensorData item) throws Exception {

        double min = item.measurements().get(0);
        double max = min;
        double sum = 0;

        for (double measurement : item.measurements()) {
            min = Math.min(min, measurement);
            max = Math.max(max, measurement);
            sum += measurement;
        }

        double avg = sum / item.measurements().size();

        return new DailyAggregatedSensorData(item.date(),
                convertToCelsius(min), convertToCelsius(max), convertToCelsius(avg));
    }

    private static double convertToCelsius(double input) {
        return (5 * (input - 32)) / 9;
    }
}
