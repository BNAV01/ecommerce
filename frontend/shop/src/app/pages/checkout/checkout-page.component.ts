import { Component } from '@angular/core';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'shop-checkout-page',
  standalone: true,
  imports: [SectionCardComponent],
  template: `
    <ui-section-card title="Checkout">
      <p class="text-sm text-slate-600">Checkout form placeholder routed through gateway to order service.</p>
    </ui-section-card>
  `
})
export class CheckoutPageComponent {}
