package com.matus.javaweatherapp.repository;

import com.matus.javaweatherapp.model.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, Long> {


    List<WeatherHistory> findByCityAndTypeOrderByRequestedAtDesc(String city, String type);

    List<WeatherHistory> findByCityOrderByRequestedAtDesc(String city);

    List<WeatherHistory> findByTypeOrderByRequestedAtDesc(String type);

    List<WeatherHistory> findAllByOrderByRequestedAtDesc();
}
