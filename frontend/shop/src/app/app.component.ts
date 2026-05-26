import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'shop-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <div class="shop-shell">
      <header class="shop-header">
        <div class="shop-container shop-header-inner">
          <a class="shop-brand" href="#inicio" aria-label="Integral X Soluciones, 3AGE SOS">
            <span class="shop-brand-sub">Integral X Soluciones</span>
            <h1 class="shop-brand-title">3AGE SOS</h1>
          </a>

          <nav class="shop-nav" aria-label="Navegacion principal">
            <a href="#solucion" class="shop-nav-link">Solucion</a>
            <a href="#licencias" class="shop-nav-link">Licencias</a>
            <a href="#equipo" class="shop-nav-link">Equipo</a>
            <a href="#contacto" class="shop-nav-link">Contacto</a>
          </nav>

          <a href="#contacto" class="shop-btn-primary">Agendar demo</a>
        </div>
      </header>

      <main class="shop-container shop-main">
        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class AppComponent {}
