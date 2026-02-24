import { Component } from '@angular/core';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'shop-cart-page',
  standalone: true,
  imports: [SectionCardComponent],
  template: `
    <ui-section-card title="Cart">
      <p class="text-sm text-slate-600">Cart state placeholder for checkout flow integration.</p>
    </ui-section-card>
  `
})
export class CartPageComponent {}
