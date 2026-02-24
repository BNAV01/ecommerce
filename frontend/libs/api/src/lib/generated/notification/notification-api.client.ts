import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../../api-base-url.token';

export interface EmailRequest {
  recipient: string;
  subject: string;
  body: string;
}

@Injectable({ providedIn: 'root' })
export class NotificationApiClient {
  private readonly apiBaseUrl = inject(API_BASE_URL);

  constructor(private readonly http: HttpClient) {}

  sendTestEmail(request: EmailRequest): Observable<unknown> {
    return this.http.post(`${this.apiBaseUrl}/api/notifications/test-email`, request);
  }
}
