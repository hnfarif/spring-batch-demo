package com.spring.batch.demo.config;

import com.spring.batch.demo.dto.DailyAggregatedSensorData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;


public class RecordConverter implements Converter {

    private final ReflectionConverter reflectionConverter;

    public RecordConverter(XStream xstream) {
        Mapper mapper = xstream.getMapper();
        reflectionConverter = new ReflectionConverter(mapper, xstream.getReflectionProvider());
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        reflectionConverter.marshal(source, writer, context);
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return reflectionConverter.unmarshal(reader, context);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.isRecord();
    }
}
