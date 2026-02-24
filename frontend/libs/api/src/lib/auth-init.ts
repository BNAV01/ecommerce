import { OAuthService, AuthConfig } from 'angular-oauth2-oidc';

export interface OidcConfigInput {
  issuer: string;
  clientId: string;
  redirectUri: string;
  postLogoutRedirectUri: string;
  scope: string;
}

export function buildOidcConfig(input: OidcConfigInput): AuthConfig {
  return {
    issuer: input.issuer,
    clientId: input.clientId,
    redirectUri: input.redirectUri,
    postLogoutRedirectUri: input.postLogoutRedirectUri,
    responseType: 'code',
    scope: input.scope,
    strictDiscoveryDocumentValidation: false,
    showDebugInformation: false,
    requireHttps: false
  };
}

export function initAuth(oauthService: OAuthService, config: OidcConfigInput): () => Promise<void> {
  return async () => {
    oauthService.configure(buildOidcConfig(config));
    await oauthService.loadDiscoveryDocumentAndTryLogin();
    if (!oauthService.hasValidAccessToken()) {
      oauthService.initCodeFlow();
    }
  };
}
