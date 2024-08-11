package com.spring.batch.demo.config;

import com.spring.batch.demo.dto.*;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:application.properties")
public class BatchConfig extends DefaultBatchConfiguration {

    @Value("classpath:input/HTE2NP.txt")
    private Resource rawDailyInputResource;
    
    @Value("file:HTE2NP.xml")
    private WritableResource aggregatedDailyOutputXmlResource;

    @Value("file:HTE2NP-anomalies.csv")
    private WritableResource anomalyDataResource;

    @Bean
    @Qualifier("aggregateSensorStep")
    public Step aggregateSensorStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){

        return new StepBuilder("aggregate-sensor", jobRepository)
                .<DailySensorData, DailyAggregatedSensorData>chunk(1, platformTransactionManager)
                .reader(new FlatFileItemReaderBuilder<DailySensorData>()
                        .name("dailySensorDataReader")
                        .resource(rawDailyInputResource)
                        .lineMapper(new SensorDataTextMapper())
                        .build())
                .processor(new RawToAggregateSensorDataProcessor())
                .writer(new StaxEventItemWriterBuilder<DailyAggregatedSensorData>()
                        .name("dailyAggregatedSensorDataWriter")
                        .marshaller(DailyAggregatedSensorData.getMarshaller())
                        .resource(aggregatedDailyOutputXmlResource)
                        .rootTagName("data")
                        .overwriteOutput(true)
                        .build())
                .build();
    }

    @Bean
    @Qualifier("reportAnomaliesStep")
    public Step reportAnomaliesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("report-anomalies", jobRepository)
                .<DailyAggregatedSensorData, AnomalyData>chunk(1, platformTransactionManager)
                .reader(new StaxEventItemReaderBuilder<DailyAggregatedSensorData>()
                        .name("dailyAggregatedSensorDataReader")
                        .unmarshaller(DailyAggregatedSensorData.getMarshaller())
                        .resource(aggregatedDailyOutputXmlResource)
                        .addFragmentRootElements(DailyAggregatedSensorData.ITEM_ROOT_ELEMENT_NAME)
                        .build())
                .processor(new SensorDataAnomalyProcessor())
                .writer(new FlatFileItemWriterBuilder<AnomalyData>()
                        .name("AnomalyDataWriter")
                        .resource(anomalyDataResource)
                        .delimited()
                        .delimiter(",")
                        .fieldExtractor(item -> new Object[] {item.date(), item.type(), item.value()})
                        .build())
                .build();
    }

}
