import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../api-base-url.token';

export interface StockResponse {
  sku: string;
  availableQuantity: number;
  reservedQuantity: number;
}

@Injectable({ providedIn: 'root' })
export class InventoryApiClient {
  private readonly apiBaseUrl = inject(API_BASE_URL);

  constructor(private readonly http: HttpClient) {}

  getStock(sku: string): Observable<StockResponse> {
    return this.http.get<StockResponse>(`${this.apiBaseUrl}/api/inventory/${sku}`);
  }

  adjustStock(payload: { sku: string; delta: number }): Observable<StockResponse> {
    return this.http.post<StockResponse>(`${this.apiBaseUrl}/api/inventory/adjustments`, payload);
  }
}
