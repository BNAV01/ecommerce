import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AdminApiService {
  private readonly cfg = environment.admin;
  private readonly gateway = this.cfg.gatewayBaseUrl;

  readonly endpoints = {
    auth: this.cfg.authBaseUrl,
    products: `${this.gateway}${this.cfg.services.products}`,
    orders: `${this.gateway}${this.cfg.services.orders}`,
    inventory: `${this.gateway}${this.cfg.services.inventory}`,
    notifications: `${this.gateway}${this.cfg.services.notifications}`,
    observability: this.cfg.observability.grafana
  } as const;

  constructor(private readonly http: HttpClient) {}

  getProducts() {
    return this.http.get(this.endpoints.products);
  }

  getOrders() {
    return this.http.get(this.endpoints.orders);
  }

  getInventory() {
    return this.http.get(this.endpoints.inventory);
  }
}
