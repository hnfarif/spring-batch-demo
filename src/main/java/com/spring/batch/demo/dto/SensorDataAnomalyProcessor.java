package com.spring.batch.demo.dto;

import org.springframework.batch.item.ItemProcessor;

public class SensorDataAnomalyProcessor implements ItemProcessor<DailyAggregatedSensorData, AnomalyData> {

    private static final double THRESHOLD = 0.9;

    @Override
    public AnomalyData process(DailyAggregatedSensorData item) throws Exception {

        if ((item.getMin() / item.getAvg()) < THRESHOLD) {
            return new AnomalyData(item.getDate(), AnomalyType.MINIMUM, item.getMin());
        } else if ((item.getAvg() / item.getMax()) < THRESHOLD) {
            return new AnomalyData(item.getDate(), AnomalyType.MAXIMUM, item.getMax());
        }else {
            return null;
        }
    }
}
