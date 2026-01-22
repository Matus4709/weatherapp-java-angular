import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { WeatherHistory, WeatherResponse } from './models';
import { WeatherService } from './services/weather.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  city = 'Warsaw';
  days = 5;

  current: WeatherResponse | null = null;
  forecast: WeatherResponse[] = [];
  history: WeatherHistory[] = [];

  historyCity = '';
  historyType = '';

  isLoading = false;
  errorMessage = '';

  readonly popularCities = ['Warsaw', 'Krakow', 'Gdansk', 'Wroclaw', 'Poznan'];

  constructor(private weatherService: WeatherService) {}

  ngOnInit(): void {
    this.search();
    this.loadHistory();
  }

  search(): void {
    const trimmedCity = this.city.trim();
    if (!trimmedCity) {
      this.errorMessage = 'Podaj nazwę miasta.';
      return;
    }

    this.city = trimmedCity;
    this.isLoading = true;
    this.errorMessage = '';

    forkJoin({
      current: this.weatherService.getCurrentWeather(this.city),
      forecast: this.weatherService.getForecast(this.city, this.days)
    }).subscribe({
      next: ({ current, forecast }) => {
        this.current = current;
        this.forecast = forecast;
        this.isLoading = false;
        this.loadHistory();
      },
      error: (err: unknown) => {
        this.isLoading = false;
        this.errorMessage = this.normalizeError(err);
      }
    });
  }

  loadHistory(): void {
    this.weatherService
      .getHistory(this.historyCity.trim() || undefined, this.historyType || undefined)
      .subscribe({
        next: (history: WeatherHistory[]) => {
          this.history = history;
        },
        error: (err: unknown) => {
          this.errorMessage = this.normalizeError(err);
        }
      });
  }

  selectCity(city: string): void {
    this.city = city;
    this.search();
  }

  trackHistory = (_: number, item: WeatherHistory): number => item.id;

  private normalizeError(err: unknown): string {
    if (typeof err === 'string') {
      return err;
    }
    if (err && typeof err === 'object') {
      const anyErr = err as { error?: { message?: string }; message?: string };
      if (anyErr.error?.message) {
        return anyErr.error.message;
      }
      if (anyErr.message) {
        return anyErr.message;
      }
    }
    return 'Nie udało się pobrać danych pogodowych.';
  }
}
