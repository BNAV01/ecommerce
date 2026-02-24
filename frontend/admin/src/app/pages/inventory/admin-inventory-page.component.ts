import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { InventoryApiClient } from '@ecommerce/api';
import { SectionCardComponent } from '@ecommerce/ui';

@Component({
  selector: 'admin-inventory-page',
  standalone: true,
  imports: [AsyncPipe, SectionCardComponent],
  template: `
    <ui-section-card title="Inventory Adjust">
      <p class="mb-4 text-sm text-slate-500">Example query for sku SKU-001 through gateway.</p>
      @if (stock$ | async; as stock) {
        <p class="text-sm">Available: {{ stock.availableQuantity }} | Reserved: {{ stock.reservedQuantity }}</p>
      } @else {
        <p class="text-sm text-slate-500">Stock not loaded yet.</p>
      }
    </ui-section-card>
  `
})
export class AdminInventoryPageComponent {
  private readonly inventoryApi = inject(InventoryApiClient);
  protected readonly stock$ = this.inventoryApi.getStock('SKU-001');
}

