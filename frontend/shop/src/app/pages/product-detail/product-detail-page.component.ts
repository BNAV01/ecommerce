import { Component, inject } from '@angular/core';
import { AsyncPipe, DecimalPipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs';
import { ProductApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'shop-product-detail-page',
  standalone: true,
  imports: [AsyncPipe, DecimalPipe, SectionCardComponent],
  template: `
    <ui-section-card title="Product Detail">
      @if (product$ | async; as product) {
        <h3 class="text-lg font-semibold">{{ product.name }}</h3>
        <p class="my-2 text-sm text-slate-600">{{ product.description }}</p>
        <p class="text-sm font-medium">{{ product.price | number: '1.2-2' }} {{ product.currency }}</p>
      } @else {
        <p class="text-sm text-slate-500">Loading product...</p>
      }
    </ui-section-card>
  `
})
export class ProductDetailPageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly productApi = inject(ProductApiClient);

  protected readonly product$ = this.route.paramMap.pipe(
    map((params) => params.get('id') ?? ''),
    switchMap((id) => this.productApi.getProduct(id))
  );
}
