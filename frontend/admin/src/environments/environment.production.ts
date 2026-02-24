export const environment = {
  production: true,
  apiBaseUrl: 'http://localhost:9000',
  auth: {
    issuer: 'http://localhost:8181/realms/ecommerce',
    clientId: 'admin-web',
    redirectUri: 'http://localhost:4201',
    postLogoutRedirectUri: 'http://localhost:4201',
    scope: 'openid profile email'
  }
};
