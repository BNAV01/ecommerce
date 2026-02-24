import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface Product {
  id: string;
  name: string;
  price: number;
  stock: number;
}

interface CreateOrderRequest {
  userId: string;
  items: Array<{ productId: string; quantity: number }>;
}

@Injectable({ providedIn: 'root' })
export class EcommerceApiService {
  private readonly config = environment.microservices;
  private readonly gateway = this.config.gatewayBaseUrl;

  readonly endpoints = {
    auth: this.config.authPath,
    product: `${this.gateway}${this.config.services.product}`,
    order: `${this.gateway}${this.config.services.order}`,
    inventory: `${this.gateway}${this.config.services.inventory}`,
    notification: `${this.gateway}${this.config.services.notification}`
  } as const;

  constructor(private readonly http: HttpClient) {}

  listProducts() {
    return this.http.get<Product[]>(this.endpoints.product);
  }

  createOrder(payload: CreateOrderRequest) {
    return this.http.post(this.endpoints.order, payload);
  }
}
