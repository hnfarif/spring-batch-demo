package com.spring.batch.demo.dto;

import org.springframework.batch.item.ItemProcessor;

public class SensorDataAnomalyProcessor implements ItemProcessor<DailyAggregatedSensorData, AnomalyData> {

    private static final double THRESHOLD = 0.9;

    @Override
    public AnomalyData process(DailyAggregatedSensorData item) throws Exception {

        if ((item.min() / item.avg()) < THRESHOLD) {
            return new AnomalyData(item.date(), AnomalyType.MINIMUM, item.min());
        } else if ((item.avg() / item.max()) < THRESHOLD) {
            return new AnomalyData(item.date(), AnomalyType.MAXIMUM, item.max());
        }else {
            return null;
        }
    }
}
