import { Component, inject } from '@angular/core';
import { AsyncPipe, DecimalPipe } from '@angular/common';
import { OrderApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'shop-orders-page',
  standalone: true,
  imports: [AsyncPipe, DecimalPipe, SectionCardComponent],
  template: `
    <ui-section-card title="Orders">
      <div class="space-y-3">
        @for (order of (orders$ | async) ?? []; track order.id) {
          <article class="rounded-lg border border-slate-200 p-3">
            <h4 class="font-semibold">Order {{ order.id }}</h4>
            <p class="text-sm">{{ order.status }} - {{ order.totalAmount | number: '1.2-2' }} {{ order.currency }}</p>
          </article>
        }
      </div>
    </ui-section-card>
  `
})
export class OrdersPageComponent {
  private readonly orderApi = inject(OrderApiClient);
  protected readonly orders$ = this.orderApi.listOrders();
}
