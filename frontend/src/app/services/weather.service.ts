import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WeatherHistory, WeatherResponse } from '../models';

@Injectable({ providedIn: 'root' })
export class WeatherService {
  constructor(private http: HttpClient) {}

  getCurrentWeather(city: string): Observable<WeatherResponse> {
    const params = new HttpParams().set('city', city);
    return this.http.get<WeatherResponse>('/weather/current', { params });
  }

  getForecast(city: string, days: number): Observable<WeatherResponse[]> {
    const params = new HttpParams().set('city', city).set('days', days);
    return this.http.get<WeatherResponse[]>('/weather/forecast', { params });
  }

  getHistory(city?: string, type?: string): Observable<WeatherHistory[]> {
    let params = new HttpParams();
    if (city) {
      params = params.set('city', city);
    }
    if (type) {
      params = params.set('type', type);
    }
    return this.http.get<WeatherHistory[]>('/weather/history', { params });
  }
}
