package com.matus.javaweatherapp.service;

import com.matus.javaweatherapp.client.OpenWeatherClient;
import com.matus.javaweatherapp.dto.WeatherResponseDTO;
import com.matus.javaweatherapp.model.WeatherHistory;
import com.matus.javaweatherapp.repository.WeatherHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherService {

    private final OpenWeatherClient weatherClient;
    private final WeatherHistoryRepository historyRepository;

    public WeatherService(OpenWeatherClient weatherClient,
                            WeatherHistoryRepository historyRepository) {
        this.weatherClient = weatherClient;
        this.historyRepository = historyRepository;
    }

    public WeatherResponseDTO getCurrentWeather(String city) {
        WeatherResponseDTO dto = weatherClient.getCurrentWeather(city);

        WeatherHistory history = new WeatherHistory();
        history.setCity(city);
        history.setDescription(dto.getDescription());
        history.setTemperature(dto.getTemperature());
        history.setHumidity(dto.getHumidity());
        history.setFeelsLike(dto.getFeelsLike());
        history.setRequestedAt(LocalDateTime.now());
        history.setForecastDate(LocalDate.now());
        history.setType("CURRENT");

        historyRepository.save(history);

        return dto;
    }

    public List<WeatherHistory> getWeatherHistory(String city, String type) {
        if (city != null && type != null) {
            return historyRepository.findByCityAndTypeOrderByRequestedAtDesc(city, type);
        } else if (city != null) {
            return historyRepository.findByCityOrderByRequestedAtDesc(city);
        } else if (type != null) {
            return historyRepository.findByTypeOrderByRequestedAtDesc(type);
        } else {
            return historyRepository.findAllByOrderByRequestedAtDesc();
        }
    }
    public List<WeatherResponseDTO> getForecastWeather(String city, int days) {
        List<WeatherResponseDTO> forecast = weatherClient.getForecast(city, days);

        //saving
        for (WeatherResponseDTO dto : forecast) {
            WeatherHistory history = new WeatherHistory();
            history.setCity(dto.getCity());
            history.setDescription(dto.getDescription());
            history.setTemperature(dto.getTemperature());
            history.setHumidity(dto.getHumidity());
            history.setFeelsLike(dto.getFeelsLike());
            history.setRequestedAt(LocalDateTime.now());
            history.setForecastDate(dto.getForecastDate());
            history.setType("FORECAST");
            historyRepository.save(history);
        }
        return forecast;
    }

}
