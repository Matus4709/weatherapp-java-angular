# WeatherApp (Java + Angular)

Nowoczesna aplikacja pogodowa z backendem w Spring Boot oraz frontendem w Angularze.

## Stack
- Backend: Java 21, Spring Boot, H2, OpenWeather API
- Frontend: Angular 17, TypeScript, RxJS

## Uruchomienie

### Backend
1. Ustaw klucz API w `backend/javaweatherapp/src/main/resources/application.properties`:
   - `weather.api.key=...`
2. Uruchom:
   - Windows: `backend/javaweatherapp/mvnw.cmd spring-boot:run`
   - Unix: `backend/javaweatherapp/./mvnw spring-boot:run`
3. API będzie pod `http://localhost:8080`

### Frontend
1. `cd frontend`
2. `npm install`
3. `npm run start:proxy`
4. Aplikacja: `http://localhost:4200`

## Endpointy API (skrót)
- `GET /weather/current?city=Warsaw`
- `GET /weather/forecast?city=Warsaw&days=1-5`
- `GET /weather/history?city=Warsaw&type=CURRENT|FORECAST`

## Informacja o autorstwie
Frontend został wykonany przy pomocy agenta AI.  
Backend został wykonany bez udziału agenta AI.
