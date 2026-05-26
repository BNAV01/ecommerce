import { Component } from '@angular/core';

type TeamMember = {
  initials: string;
  name: string;
  role: string;
  description: string;
};

@Component({
  selector: 'shop-sales-page',
  standalone: true,
  template: `
    <section id="inicio" class="sales-hero">
      <div class="hero-copy">
        <p class="section-kicker">Integral X Soluciones</p>
        <h2>3AGE SOS para respuesta asistencial coordinada.</h2>
        <p class="hero-lead">
          Una app enfocada en activar alertas SOS, ordenar la primera respuesta y entregar visibilidad
          operativa a clinicas, equipos de salud y responsables legales desde un mismo flujo.
        </p>

        <div class="hero-actions">
          <a class="shop-btn-primary" href="#contacto">Agendar demo</a>
          <a class="shop-btn-secondary" href="#licencias">Ver licencias</a>
        </div>

        <dl class="proof-row" aria-label="Focos de 3AGE SOS">
          <div>
            <dt>SOS</dt>
            <dd>Activacion priorizada</dd>
          </div>
          <div>
            <dt>Triage</dt>
            <dd>Registro inicial guiado</dd>
          </div>
          <div>
            <dt>Gestion</dt>
            <dd>Seguimiento operativo</dd>
          </div>
        </dl>
      </div>

      <aside class="app-preview" aria-label="Vista previa de la app 3AGE SOS">
        <div class="preview-bar">
          <span></span>
          <strong>3AGE SOS</strong>
          <small>Operacion activa</small>
        </div>

        <div class="sos-status">
          <span class="status-dot"></span>
          <div>
            <p>Alerta SOS recibida</p>
            <strong>Paciente priorizado para derivacion</strong>
          </div>
        </div>

        <div class="triage-grid">
          <article>
            <span>Prioridad</span>
            <strong>Alta</strong>
          </article>
          <article>
            <span>Tiempo</span>
            <strong>02:18</strong>
          </article>
          <article>
            <span>Equipo</span>
            <strong>Asignado</strong>
          </article>
        </div>

        <ol class="response-flow">
          <li>Contacto registrado</li>
          <li>Equipo notificado</li>
          <li>Seguimiento disponible</li>
        </ol>
      </aside>
    </section>

    <section id="solucion" class="page-section">
      <div class="section-head">
        <p class="section-kicker">Solucion</p>
        <h2>Una operacion clara para momentos criticos.</h2>
        <p>
          Integral X Soluciones posiciona 3AGE SOS como una herramienta de coordinacion para instituciones
          que necesitan rapidez, trazabilidad y control sobre cada solicitud de apoyo.
        </p>
      </div>

      <div class="value-grid">
        <article>
          <span>01</span>
          <h3>Alerta y recepcion</h3>
          <p>El flujo SOS centraliza la solicitud, identifica el origen y deja la informacion lista para actuar.</p>
        </article>
        <article>
          <span>02</span>
          <h3>Triage inicial</h3>
          <p>Los datos relevantes se capturan de forma estructurada para orientar la respuesta del equipo.</p>
        </article>
        <article>
          <span>03</span>
          <h3>Seguimiento operativo</h3>
          <p>Los responsables pueden revisar estado, responsables y acciones realizadas sin perder contexto.</p>
        </article>
      </div>
    </section>

    <section id="licencias" class="page-section license-section">
      <div class="section-head">
        <p class="section-kicker">Licencias</p>
        <h2>Precio ajustado al volumen real de pacientes.</h2>
        <p>
          3AGE SOS no se vende como una licencia fija por usuario. El valor se define segun el volumen de
          pacientes del centro medico, para que cada clinica pague de acuerdo con su escala operativa.
        </p>
      </div>

      <div class="license-layout">
        <article class="license-card">
          <h3>Licenciamiento por volumen</h3>
          <p>
            Evaluamos cantidad de pacientes, demanda esperada de alertas SOS, cantidad de sedes y nivel de
            acompanamiento requerido. Con esos datos se construye una propuesta comercial proporcional.
          </p>
          <ul>
            <li>Centros pequenos con baja carga asistencial.</li>
            <li>Clinicas medianas con operacion recurrente.</li>
            <li>Instituciones de alto volumen o multiples sedes.</li>
          </ul>
        </article>

        <aside class="license-summary">
          <span>Modelo comercial</span>
          <strong>Escalable por pacientes</strong>
          <p>El precio final se confirma despues de revisar el volumen mensual y el alcance de implementacion.</p>
        </aside>
      </div>
    </section>

    <section id="equipo" class="page-section">
      <div class="section-head">
        <p class="section-kicker">Equipo</p>
        <h2>Equipo responsable del proyecto.</h2>
        <p>Por ahora se muestran solo los roles confirmados para la comunicacion comercial de 3AGE SOS.</p>
      </div>

      <div class="team-grid">
        @for (member of teamMembers; track member.name) {
          <article class="team-card">
            <div class="avatar" aria-hidden="true">{{ member.initials }}</div>
            <div>
              <h3>{{ member.name }}</h3>
              <p class="team-role">{{ member.role }}</p>
            </div>
            <p class="team-description">{{ member.description }}</p>
          </article>
        }

        @if (showThirdTeamMember) {
          <article class="team-card" aria-hidden="true"></article>
        }
      </div>
    </section>

    <section id="contacto" class="contact-section">
      <div class="contact-copy">
        <p class="section-kicker">Contacto</p>
        <h2>Agenda una demo de 3AGE SOS.</h2>
        <p>
          Cuentanos sobre la clinica, el representante legal y el volumen aproximado de pacientes.
          Con esa informacion podemos preparar una propuesta acorde a tu centro medico.
        </p>

        <aside class="direct-contact">
          <h3>Contacto directo</h3>
          <div>
            <span>Email</span>
            <a href="mailto:contacto@ixstriage.com">contacto&#64;ixstriage.com</a>
          </div>
        </aside>
      </div>

      <form class="demo-form" autocomplete="on">
        <div class="form-row">
          <label>
            Representante legal
            <input name="legalRepresentative" placeholder="Nombre y apellido" />
          </label>
          <label>
            Clinica
            <input name="clinic" placeholder="Nombre de la institucion" />
          </label>
        </div>

        <div class="form-row">
          <label>
            Email corporativo
            <input name="email" type="email" placeholder="nombre@clinica.com" />
          </label>
          <label>
            Telefono
            <input name="phone" type="tel" placeholder="+54 9 11 2345 6789" />
          </label>
        </div>

        <div class="form-row">
          <label>
            Volumen de pacientes
            <input name="patientVolume" placeholder="Ej: 2.000 pacientes mensuales" />
          </label>
          <label>
            Tipo de centro
            <input name="centerType" placeholder="Clinica, red medica o centro medico" />
          </label>
        </div>

        <label>
          Necesidad principal
          <textarea
            name="message"
            rows="5"
            placeholder="Cuéntanos que flujo SOS o de respuesta asistencial necesitas ordenar"
          ></textarea>
        </label>

        <button type="button" class="shop-btn-primary">Solicitar demo</button>
      </form>
    </section>
  `,
  styles: [`
    :host {
      display: block;
    }

    .sales-hero,
    .page-section,
    .contact-section {
      scroll-margin-top: 96px;
    }

    .sales-hero {
      display: grid;
      grid-template-columns: minmax(0, 1.08fr) minmax(320px, 0.92fr);
      gap: 2rem;
      align-items: center;
      min-height: calc(100vh - 92px);
      padding: 3rem 0 2.2rem;
    }

    .section-kicker {
      margin: 0 0 0.85rem;
      color: var(--shop-accent);
      font-size: 0.76rem;
      font-weight: 800;
      letter-spacing: 0.28em;
      text-transform: uppercase;
    }

    h2,
    h3,
    p,
    dl,
    ol,
    ul {
      margin: 0;
    }

    .hero-copy h2 {
      color: var(--shop-ink);
      font-size: clamp(2.8rem, 7vw, 5.8rem);
      line-height: 0.94;
      max-width: 9.5ch;
    }

    .hero-lead {
      margin-top: 1.4rem;
      max-width: 62ch;
      color: var(--shop-muted);
      font-size: clamp(1rem, 1.5vw, 1.18rem);
      line-height: 1.7;
    }

    .hero-actions {
      display: flex;
      flex-wrap: wrap;
      gap: 0.75rem;
      margin-top: 1.6rem;
    }

    .proof-row {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 0.75rem;
      margin-top: 2rem;
      max-width: 620px;
    }

    .proof-row div,
    .value-grid article,
    .team-card,
    .direct-contact,
    .demo-form,
    .license-card,
    .license-summary,
    .app-preview {
      border: 1px solid var(--shop-border);
      border-radius: 8px;
      background: #ffffff;
      box-shadow: 0 14px 34px rgba(20, 36, 47, 0.07);
    }

    .proof-row div {
      padding: 0.9rem;
    }

    .proof-row dt {
      color: var(--shop-blue);
      font-size: 1.35rem;
      font-weight: 850;
    }

    .proof-row dd {
      margin: 0.25rem 0 0;
      color: var(--shop-muted);
      font-size: 0.88rem;
      line-height: 1.4;
    }

    .app-preview {
      background:
        linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(249, 252, 251, 0.98)),
        linear-gradient(135deg, rgba(0, 127, 115, 0.12), rgba(47, 95, 143, 0.1));
      padding: 1rem;
    }

    .preview-bar {
      display: grid;
      grid-template-columns: 36px 1fr auto;
      align-items: center;
      gap: 0.75rem;
      border-bottom: 1px solid var(--shop-border);
      padding-bottom: 0.85rem;
    }

    .preview-bar span {
      width: 36px;
      height: 36px;
      border-radius: 8px;
      background: #153a5b;
      position: relative;
    }

    .preview-bar span::after {
      content: '3';
      position: absolute;
      inset: 0;
      display: grid;
      place-items: center;
      color: #ffffff;
      font-weight: 900;
    }

    .preview-bar small {
      color: var(--shop-accent);
      font-size: 0.78rem;
      font-weight: 800;
    }

    .sos-status {
      display: flex;
      gap: 0.85rem;
      margin-top: 1rem;
      border: 1px solid rgba(190, 57, 57, 0.18);
      border-radius: 8px;
      background: #fff7f5;
      padding: 1rem;
    }

    .status-dot {
      flex: 0 0 auto;
      width: 12px;
      height: 12px;
      border-radius: 999px;
      background: #c7372f;
      margin-top: 0.28rem;
      box-shadow: 0 0 0 6px rgba(199, 55, 47, 0.12);
    }

    .sos-status p {
      color: #8a2a24;
      font-size: 0.8rem;
      font-weight: 800;
      letter-spacing: 0.06em;
      text-transform: uppercase;
    }

    .sos-status strong {
      display: block;
      margin-top: 0.25rem;
      color: #2a2522;
      font-size: 1.1rem;
      line-height: 1.35;
    }

    .triage-grid {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 0.65rem;
      margin-top: 0.85rem;
    }

    .triage-grid article {
      border: 1px solid var(--shop-border);
      border-radius: 8px;
      padding: 0.85rem;
    }

    .triage-grid span,
    .direct-contact span,
    .license-summary span {
      display: block;
      color: var(--shop-accent);
      font-size: 0.72rem;
      font-weight: 900;
      letter-spacing: 0.14em;
      text-transform: uppercase;
    }

    .triage-grid strong,
    .license-summary strong {
      display: block;
      margin-top: 0.35rem;
      color: var(--shop-ink);
      font-size: 1rem;
    }

    .response-flow {
      display: grid;
      gap: 0.55rem;
      margin-top: 0.85rem;
      padding: 0;
      list-style: none;
      counter-reset: response;
    }

    .response-flow li {
      display: flex;
      align-items: center;
      gap: 0.65rem;
      color: var(--shop-muted);
      font-size: 0.92rem;
    }

    .response-flow li::before {
      counter-increment: response;
      content: counter(response);
      display: grid;
      place-items: center;
      width: 24px;
      height: 24px;
      border-radius: 999px;
      background: #e7f2f0;
      color: var(--shop-accent-strong);
      font-size: 0.75rem;
      font-weight: 900;
    }

    .page-section {
      padding: 4rem 0 1rem;
    }

    .section-head {
      max-width: 760px;
    }

    .section-head h2,
    .contact-copy h2 {
      color: var(--shop-ink);
      font-size: clamp(2rem, 4vw, 3.35rem);
      line-height: 1.02;
    }

    .section-head p:not(.section-kicker),
    .contact-copy > p,
    .license-card p,
    .license-summary p {
      margin-top: 1rem;
      color: var(--shop-muted);
      font-size: 1rem;
      line-height: 1.7;
    }

    .value-grid,
    .team-grid {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 1rem;
      margin-top: 1.4rem;
    }

    .value-grid article,
    .team-card {
      padding: 1.1rem;
    }

    .value-grid span {
      color: var(--shop-blue);
      font-size: 0.78rem;
      font-weight: 900;
      letter-spacing: 0.14em;
    }

    .value-grid h3,
    .license-card h3,
    .team-card h3,
    .direct-contact h3 {
      color: var(--shop-ink);
      font-size: 1.18rem;
    }

    .value-grid p,
    .team-description {
      margin-top: 0.65rem;
      color: var(--shop-muted);
      line-height: 1.6;
      font-size: 0.95rem;
    }

    .license-layout {
      display: grid;
      grid-template-columns: minmax(0, 1.2fr) minmax(300px, 0.8fr);
      gap: 1rem;
      margin-top: 1.4rem;
    }

    .license-card,
    .license-summary {
      padding: 1.2rem;
    }

    .license-card ul {
      display: grid;
      gap: 0.55rem;
      margin-top: 1rem;
      padding-left: 1.1rem;
      color: var(--shop-muted);
      line-height: 1.5;
    }

    .license-summary {
      align-self: stretch;
      display: grid;
      align-content: center;
    }

    .license-summary strong {
      color: var(--shop-blue);
      font-size: clamp(1.8rem, 4vw, 3rem);
      line-height: 1;
    }

    .team-grid {
      grid-template-columns: repeat(2, minmax(0, 1fr));
    }

    .team-card {
      display: grid;
      grid-template-columns: 64px 1fr;
      gap: 1rem;
      align-items: center;
    }

    .avatar {
      display: grid;
      place-items: center;
      width: 64px;
      height: 64px;
      border-radius: 8px;
      background: #e4efed;
      color: var(--shop-accent-strong);
      font-weight: 900;
    }

    .team-role {
      margin-top: 0.2rem;
      color: var(--shop-blue);
      font-size: 0.92rem;
      font-weight: 800;
    }

    .team-description {
      grid-column: 1 / -1;
    }

    .contact-section {
      display: grid;
      grid-template-columns: minmax(0, 0.92fr) minmax(360px, 1.08fr);
      gap: 1.4rem;
      align-items: start;
      padding: 4rem 0 4.5rem;
    }

    .direct-contact,
    .demo-form {
      padding: 1.25rem;
    }

    .direct-contact {
      margin-top: 1.4rem;
    }

    .direct-contact div {
      margin-top: 1rem;
    }

    .direct-contact a {
      display: inline-flex;
      margin-top: 0.35rem;
      color: var(--shop-ink);
      font-weight: 800;
    }

    .demo-form {
      display: grid;
      gap: 1rem;
    }

    .form-row {
      display: grid;
      grid-template-columns: repeat(2, minmax(0, 1fr));
      gap: 1rem;
    }

    label {
      display: grid;
      gap: 0.45rem;
      color: #43515a;
      font-size: 0.86rem;
      font-weight: 850;
    }

    input,
    textarea {
      width: 100%;
      border: 1px solid #c9d3d0;
      border-radius: 8px;
      background: #fbfcfb;
      color: var(--shop-ink);
      font: inherit;
      padding: 0.78rem 0.85rem;
    }

    textarea {
      resize: vertical;
      min-height: 132px;
    }

    input:focus,
    textarea:focus {
      outline: 3px solid rgba(0, 127, 115, 0.16);
      border-color: var(--shop-accent);
      background: #ffffff;
    }

    .demo-form .shop-btn-primary {
      width: fit-content;
    }

    @media (max-width: 980px) {
      .sales-hero,
      .contact-section,
      .license-layout {
        grid-template-columns: 1fr;
      }

      .sales-hero {
        min-height: auto;
        padding-top: 2rem;
      }

      .hero-copy h2 {
        max-width: 12ch;
      }
    }

    @media (max-width: 760px) {
      .proof-row,
      .value-grid,
      .team-grid,
      .form-row,
      .triage-grid {
        grid-template-columns: 1fr;
      }

      .sales-hero {
        padding-top: 1.25rem;
      }

      .hero-copy h2 {
        font-size: clamp(2.4rem, 16vw, 4rem);
        max-width: 10ch;
      }

      .preview-bar {
        grid-template-columns: 36px 1fr;
      }

      .preview-bar small {
        grid-column: 2;
      }

      .page-section,
      .contact-section {
        padding-top: 3rem;
      }
    }
  `]
})
export class SalesPageComponent {
  protected readonly teamMembers: TeamMember[] = [
    {
      initials: 'MP',
      name: 'Maria Pantó',
      role: 'Gerente del proyecto',
      description:
        'Coordina prioridades, alcance comercial y validacion funcional para que 3AGE SOS responda a necesidades reales de operacion.'
    },
    {
      initials: 'BN',
      name: 'Benjamin Navarrete',
      role: 'Lider Tecnico',
      description:
        'Define arquitectura, integraciones y criterios de seguridad para una app estable, mantenible y preparada para escalar.'
    }
  ];

  protected readonly showThirdTeamMember = false;
}
