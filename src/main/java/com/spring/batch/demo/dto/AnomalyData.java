package com.spring.batch.demo.dto;

public record AnomalyData(String date, AnomalyType type, double value) {
}
