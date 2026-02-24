import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../api-base-url.token';

export interface Product {
  id: string;
  sku: string;
  name: string;
  description: string;
  price: number;
  currency: string;
  active: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProductApiClient {
  private readonly apiBaseUrl = inject(API_BASE_URL);

  constructor(private readonly http: HttpClient) {}

  listProducts(): Observable<{ content: Product[] }> {
    return this.http.get<{ content: Product[] }>(`${this.apiBaseUrl}/api/products`);
  }

  getProduct(id: string): Observable<Product> {
    return this.http.get<Product>(`${this.apiBaseUrl}/api/products/${id}`);
  }
}
