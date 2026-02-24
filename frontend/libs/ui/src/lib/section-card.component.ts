import { Component, Input } from '@angular/core';

@Component({
  selector: 'ui-section-card',
  standalone: true,
  template: `
    <section class="rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
      <h2 class="mb-3 text-xl font-semibold text-slate-900">{{ title }}</h2>
      <ng-content></ng-content>
    </section>
  `
})
export class SectionCardComponent {
  @Input({ required: true }) title!: string;
}
