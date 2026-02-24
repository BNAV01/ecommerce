import { Component, inject } from '@angular/core';
import { AsyncPipe, DecimalPipe } from '@angular/common';
import { OrderApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'admin-orders-page',
  standalone: true,
  imports: [AsyncPipe, DecimalPipe, SectionCardComponent],
  template: `
    <ui-section-card title="Orders View">
      <div class="space-y-2">
        @for (order of (orders$ | async) ?? []; track order.id) {
          <article class="rounded-lg border border-slate-200 p-3 text-sm">
            <div class="font-semibold">{{ order.id }}</div>
            <div>{{ order.status }} - {{ order.totalAmount | number: '1.2-2' }} {{ order.currency }}</div>
          </article>
        }
      </div>
    </ui-section-card>
  `
})
export class AdminOrdersPageComponent {
  private readonly orderApi = inject(OrderApiClient);
  protected readonly orders$ = this.orderApi.listOrders();
}
