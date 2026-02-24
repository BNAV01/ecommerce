export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:9000',
  auth: {
    issuer: 'http://localhost:8181/realms/ecommerce',
    clientId: 'shop-web',
    redirectUri: 'http://localhost:4200',
    postLogoutRedirectUri: 'http://localhost:4200',
    scope: 'openid profile email'
  }
};
