import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupRequestPayload } from '../signup/signup-request.payload';
import { Observable } from 'rxjs';
import { LoginRequestPayload } from '../login/login-request.payload';
import { LoginResponse } from '../login/login-response.payload';
import { LocalStorageService } from 'ngx-webstorage';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private httpClient: HttpClient,
    private localStorage: LocalStorageService
  ) {}

  signup(signUpRequest: SignupRequestPayload): Observable<any> {
    return this.httpClient.post(
      'http://localhost:8080/api/auth/signup',
      signUpRequest,
      { responseType: 'text' }
    );
  }

  login(loginRequest: LoginRequestPayload): Observable<boolean> {
    return this.httpClient
      .post<LoginResponse>('http://localhost:8080/api/auth/login', loginRequest)
      .pipe(
        map((response) => {
          this.localStorage.store(
            'authenticationToken',
            response.authenticationToken
          );
          this.localStorage.store('refreshToken', response.refreshToken);
          this.localStorage.store('expiresAt', response.expiresAt);
          this.localStorage.store('username', response.username);

          return true;
        })
      );
  }
}
