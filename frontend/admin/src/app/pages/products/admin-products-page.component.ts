import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { ProductApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'admin-products-page',
  standalone: true,
  imports: [AsyncPipe, SectionCardComponent],
  template: `
    <ui-section-card title="Products CRUD">
      <p class="mb-4 text-sm text-slate-500">Read list via gateway. Create/update/delete actions can be bound to forms.</p>
      <ul class="space-y-2">
        @for (item of (products$ | async)?.content ?? []; track item.id) {
          <li class="rounded-lg border border-slate-200 p-3 text-sm">
            <span class="font-semibold">{{ item.name }}</span>
            <span class="ml-2 text-slate-500">{{ item.sku }}</span>
          </li>
        }
      </ul>
    </ui-section-card>
  `
})
export class AdminProductsPageComponent {
  private readonly productApi = inject(ProductApiClient);
  protected readonly products$ = this.productApi.listProducts();
}
