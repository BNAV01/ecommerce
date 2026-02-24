import { Component, inject } from '@angular/core';
import { AsyncPipe, DecimalPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'shop-catalog-page',
  standalone: true,
  imports: [AsyncPipe, DecimalPipe, RouterLink, SectionCardComponent],
  template: `
    <ui-section-card title="Catalog">
      <p class="mb-4 text-sm text-slate-500">Products fetched through API Gateway.</p>
      <div class="grid gap-4 md:grid-cols-3">
        @for (item of (products$ | async)?.content ?? []; track item.id) {
          <article class="rounded-xl border border-slate-200 bg-slate-50 p-4">
            <h3 class="font-semibold">{{ item.name }}</h3>
            <p class="mt-1 text-xs text-slate-500">{{ item.sku }}</p>
            <p class="mt-2 text-sm">{{ item.price | number: '1.2-2' }} {{ item.currency }}</p>
            <a [routerLink]="['/products', item.id]" class="mt-3 inline-block text-sm font-semibold text-brand-700">Details</a>
          </article>
        }
      </div>
    </ui-section-card>
  `
})
export class CatalogPageComponent {
  private readonly productApi = inject(ProductApiClient);
  protected readonly products$ = this.productApi.listProducts();
}
