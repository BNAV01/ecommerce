import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EcommerceApiService } from './core/services/ecommerce-api.service';
import { environment } from '../environments/environment';

type ServiceCard = {
  name: string;
  description: string;
  url: string;
  port: string;
};

@Component({
  selector: 'app-root',
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = 'Shop Frontend';
  protected readonly gatewayUrl = environment.microservices.gatewayBaseUrl;
  protected readonly serviceCards: ServiceCard[];

  constructor(private readonly api: EcommerceApiService) {
    this.serviceCards = [
      {
        name: 'Auth / Keycloak',
        description: 'Authentication flow via API Gateway to security layer.',
        url: this.api.endpoints.auth,
        port: '8181'
      },
      {
        name: 'Product Service',
        description: 'Catalog and product data.',
        url: this.api.endpoints.product,
        port: '8800'
      },
      {
        name: 'Order Service',
        description: 'Order orchestration with resilience patterns.',
        url: this.api.endpoints.order,
        port: '8801'
      },
      {
        name: 'Inventory Service',
        description: 'Stock reservation and inventory updates.',
        url: this.api.endpoints.inventory,
        port: '8802'
      },
      {
        name: 'Notification Service',
        description: 'Event-driven notifications from Kafka events.',
        url: this.api.endpoints.notification,
        port: '8803'
      }
    ];
  }
}
