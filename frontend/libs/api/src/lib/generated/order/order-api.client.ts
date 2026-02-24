import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../api-base-url.token';

export interface OrderItemRequest {
  sku: string;
  quantity: number;
  unitPrice: number;
}

export interface CreateOrderRequest {
  customerEmail: string;
  currency: string;
  items: OrderItemRequest[];
}

export interface OrderResponse {
  id: string;
  status: string;
  customerEmail: string;
  totalAmount: number;
  currency: string;
}

@Injectable({ providedIn: 'root' })
export class OrderApiClient {
  private readonly apiBaseUrl = inject(API_BASE_URL);

  constructor(private readonly http: HttpClient) {}

  createOrder(request: CreateOrderRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(`${this.apiBaseUrl}/api/orders`, request);
  }

  listOrders(): Observable<OrderResponse[]> {
    return this.http.get<OrderResponse[]>(`${this.apiBaseUrl}/api/orders`);
  }
}
