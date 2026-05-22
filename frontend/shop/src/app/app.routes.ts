import { Routes } from '@angular/router';
import { SalesPageComponent } from './pages/sales/sales-page.component';

export const routes: Routes = [
  { path: '', component: SalesPageComponent },
  { path: '**', redirectTo: '' }
];
