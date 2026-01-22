package com.matus.javaweatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDTO {

    private String city;
    private String description;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private LocalDate forecastDate;
}
