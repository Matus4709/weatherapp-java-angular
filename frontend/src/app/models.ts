export interface WeatherResponse {
  city: string;
  description: string;
  temperature: number;
  feelsLike: number;
  humidity: number;
  forecastDate: string;
}

export interface WeatherHistory {
  id: number;
  city: string;
  description: string;
  temperature: number;
  feelsLike: number;
  humidity: number;
  requestedAt: string;
  forecastDate: string;
  type: string;
}
