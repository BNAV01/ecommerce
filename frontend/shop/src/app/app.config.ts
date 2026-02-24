import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideOAuthClient, OAuthService } from 'angular-oauth2-oidc';
import { API_BASE_URL, authTokenInterceptor, initAuth } from '@ecommerce/api';
import { routes } from './app.routes';
import { environment } from '../environments/environment';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideOAuthClient(),
    provideHttpClient(withInterceptors([authTokenInterceptor])),
    { provide: API_BASE_URL, useValue: environment.apiBaseUrl },
    {
      provide: APP_INITIALIZER,
      multi: true,
      deps: [OAuthService],
      useFactory: (oauth: OAuthService) => initAuth(oauth, environment.auth)
    }
  ]
};
