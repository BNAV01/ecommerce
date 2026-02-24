import { Routes } from '@angular/router';
import { CatalogPageComponent } from './pages/catalog/catalog-page.component';
import { ProductDetailPageComponent } from './pages/product-detail/product-detail-page.component';
import { CartPageComponent } from './pages/cart/cart-page.component';
import { CheckoutPageComponent } from './pages/checkout/checkout-page.component';
import { OrdersPageComponent } from './pages/orders/orders-page.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'catalog' },
  { path: 'catalog', component: CatalogPageComponent },
  { path: 'products/:id', component: ProductDetailPageComponent },
  { path: 'cart', component: CartPageComponent },
  { path: 'checkout', component: CheckoutPageComponent },
  { path: 'orders', component: OrdersPageComponent }
];
