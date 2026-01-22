package com.matus.javaweatherapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String description;
    private double temperature;
    private double feelsLike;
    private double humidity;
    private LocalDateTime requestedAt;
    private LocalDate forecastDate;
    private String type; // Current / Forcast
}
