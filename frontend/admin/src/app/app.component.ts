import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'admin-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="min-h-screen">
      <header class="border-b border-slate-300 bg-white">
        <div class="mx-auto flex max-w-6xl items-center justify-between px-6 py-4">
          <h1 class="text-xl font-bold">Admin Console</h1>
          <nav class="flex items-center gap-3 text-sm font-medium">
            <a routerLink="/products" routerLinkActive="text-brand-700" class="text-slate-600">Products</a>
            <a routerLink="/inventory" routerLinkActive="text-brand-700" class="text-slate-600">Inventory</a>
            <a routerLink="/orders" routerLinkActive="text-brand-700" class="text-slate-600">Orders</a>
          </nav>
          <button
            type="button"
            class="rounded-lg bg-brand-500 px-3 py-2 text-xs font-semibold text-white"
            (click)="logout()"
          >
            Logout
          </button>
        </div>
      </header>

      <main class="mx-auto max-w-6xl px-6 py-6">
        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class AppComponent {
  constructor(private readonly oauthService: OAuthService) {}

  logout(): void {
    this.oauthService.logOut();
  }
}
