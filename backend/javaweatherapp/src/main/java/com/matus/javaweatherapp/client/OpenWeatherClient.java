package com.matus.javaweatherapp.client;

import com.matus.javaweatherapp.dto.WeatherResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    private final String forecastApiUrl;

    public OpenWeatherClient(
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.forecast.url}") String forecastApiUrl
    ) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.forecastApiUrl = forecastApiUrl;
    }

    @SuppressWarnings("deprecation")
    public WeatherResponseDTO getCurrentWeather(String city) {
        String json = getCurrentWeatherRaw(city);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            String description = root.path("weather").get(0).path("description").asText();
            double temp = root.path("main").path("temp").asDouble();
            double feelsLike = root.path("main").path("feels_like").asDouble();
            int humidity = root.path("main").path("humidity").asInt();

            return new WeatherResponseDTO(city, description, temp, feelsLike, humidity, null);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing weather JSON", e);
        }
    }

    public String getCurrentWeatherRaw(String city) {
        String url = String.format(
                "%s?q=%s&appid=%s&units=metric",
                apiUrl,
                city,
                apiKey
        );

        return restTemplate.getForObject(url, String.class);
    }

    @SuppressWarnings("deprecation")
    public List<WeatherResponseDTO> getForecast(String city, int days) {
        String url = String.format(
                "%s?q=%s&appid=%s&units=metric",
                forecastApiUrl,
                city,
                apiKey
        );
        try {
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            List<WeatherResponseDTO> forecastList = new ArrayList<>();
            Set<LocalDate> addedDates = new HashSet<>();

            for (JsonNode itemNode : root.path("list")) {
                String dateText = itemNode.path("dt_txt").asText(null);
                if (dateText == null || dateText.length() < 10) {
                    continue;
                }
                LocalDate date = LocalDate.parse(dateText.substring(0, 10));
                if (!addedDates.add(date)) {
                    continue;
                }

                JsonNode mainNode = itemNode.path("main");
                double temp = mainNode.path("temp").asDouble();
                double feelsLike = mainNode.path("feels_like").asDouble();
                int humidity = mainNode.path("humidity").asInt();
                String description = itemNode.path("weather").get(0).path("description").asText();

                forecastList.add(new WeatherResponseDTO(
                        city,
                        description,
                        temp,
                        feelsLike,
                        humidity,
                        date
                ));

                if (forecastList.size() >= days) {
                    break;
                }
            }
            return forecastList;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing forecast JSON", e);
        }
    }
}
