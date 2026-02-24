import { Routes } from '@angular/router';
import { AdminProductsPageComponent } from './pages/products/admin-products-page.component';
import { AdminInventoryPageComponent } from './pages/inventory/admin-inventory-page.component';
import { AdminOrdersPageComponent } from './pages/orders/admin-orders-page.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'products' },
  { path: 'products', component: AdminProductsPageComponent },
  { path: 'inventory', component: AdminInventoryPageComponent },
  { path: 'orders', component: AdminOrdersPageComponent }
];
