package com.matus.javaweatherapp.controller;

import com.matus.javaweatherapp.dto.WeatherResponseDTO;
import com.matus.javaweatherapp.model.WeatherHistory;
import com.matus.javaweatherapp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/current")
    public WeatherResponseDTO currentWeather(@RequestParam String city){
        return weatherService.getCurrentWeather(city);
    }

    @GetMapping("/weather/history")
    public List<WeatherHistory> getHistory(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String type
    ){
      return weatherService.getWeatherHistory(city, type);
    }

    @GetMapping("/weather/forecast")
    public List<WeatherResponseDTO> forecast(
            @RequestParam String city,
            @RequestParam(defaultValue = "5") int days
    ) {
        if (days < 1 || days > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Free plan supports 1-5 days of forecast"
            );
        }
        return weatherService.getForecastWeather(city,days);
    }
}
