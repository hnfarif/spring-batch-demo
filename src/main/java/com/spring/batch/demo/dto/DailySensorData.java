package com.spring.batch.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


public record DailySensorData(String date, List<Double> measurements) {
}
