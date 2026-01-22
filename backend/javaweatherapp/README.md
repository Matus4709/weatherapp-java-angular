# Java Weather App (Backend)

Projekt backendowy do portfolio. Aplikacja udostepnia REST API z biezaca pogoda, prognoza (free plan) oraz historia zapytan zapisywana w bazie H2.

## Stack
- Java 21
- Spring Boot 4.0.1 (WebMVC, Data JPA)
- H2 (in-memory)
- Jackson
- OpenWeather API (free plan)

## Jak uruchomic
1. Wpisz swoj klucz API w `src/main/resources/application.properties`:
   - `weather.api.key=...`
2. Uruchom:
   - Windows: `mvnw.cmd spring-boot:run`
   - Unix: `./mvnw spring-boot:run`
3. API bedzie pod:
   - `http://localhost:8080`

## Ograniczenia free plan
Free plan OpenWeather zwraca prognoze tylko na 5 dni (co 3 godziny). API przyjmuje `days` tylko w zakresie 1-5. W innym przypadku zwraca 400.

## Endpointy (dla frontendowca)
### 1) Biezaca pogoda
`GET /weather/current?city=Warsaw`

Przyklad odpowiedzi:
```json
{
  "city": "Warsaw",
  "description": "clear sky",
  "temperature": 3.2,
  "feelsLike": 0.5,
  "humidity": 67,
  "forecastDate": null
}
```

### 2) Prognoza (1-5 dni)
`GET /weather/forecast?city=Warsaw&days=5`

Wynik to lista, gdzie kazdy element reprezentuje jeden dzien (pierwszy wpis z danego dnia w danych 3h).

Przyklad odpowiedzi:
```json
[
  {
    "city": "Warsaw",
    "description": "light rain",
    "temperature": 2.1,
    "feelsLike": -1.0,
    "humidity": 80,
    "forecastDate": "2026-01-22"
  }
]
```

Walidacja:
- `days < 1` lub `days > 5` -> HTTP 400
- komunikat: `Free plan supports 1-5 days of forecast`

### 3) Historia zapytan
`GET /weather/history`

Opcjonalne filtry:
- `city` (np. `?city=Warsaw`)
- `type` (`CURRENT` lub `FORECAST`)

Przyklad odpowiedzi:
```json
[
  {
    "id": 1,
    "city": "Warsaw",
    "description": "clear sky",
    "temperature": 3.2,
    "feelsLike": 0.5,
    "humidity": 67,
    "requestedAt": "2026-01-22T12:34:56.123",
    "forecastDate": null,
    "type": "CURRENT"
  }
]
```

## Konfiguracja
Plik: `src/main/resources/application.properties`
- `weather.api.key` - klucz OpenWeather
- `weather.api.url` - endpoint biezacej pogody
- `weather.api.forecast.url` - endpoint prognozy (uzywa `/forecast`)

## H2 Console
Domyslnie wlaczone:
- `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`

