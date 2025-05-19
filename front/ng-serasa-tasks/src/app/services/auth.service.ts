import { Injectable, signal } from "@angular/core";
import { AuthResponse, LoginData, SignUpData } from "../models/auth";
import { Profile } from "../models/profile";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { ToasterService } from "./toaster.service";
import { environment } from '../../environments/environment';
@Injectable({
    providedIn: 'root'
  })
  export class AuthService {
    private baseUrl = environment.apiUrl;
    private apiUrl = `${this.baseUrl}/api/auth`;
    private accountApiSignupUrl = `${this.baseUrl}/api/account`;
    private profile = signal<Profile | undefined>(undefined);
    private loggedIn = signal(false);
    private token = signal<string | undefined>(undefined);
    constructor(private http: HttpClient, private router: Router, private toasterService: ToasterService) {
        this.verifyAuthStorage()

    }

    
    verifyAuthStorage() {
        const profileRaw = localStorage.getItem('profile');
        const tokenRaw = localStorage.getItem('authToken');
        if (profileRaw && tokenRaw) {
            this.profile.set(JSON.parse(profileRaw))
            this.token.set(tokenRaw);
            this.loggedIn.set(true);
        } else {
            this.profile.set(undefined)
            this.token.set(undefined);
            this.loggedIn.set(false);
        }
    }
    handleLogin(data: LoginData) {
        this.http.post<AuthResponse>(this.apiUrl + '/login', data).subscribe(response  => {
            this.profile.set(response.profile);
            this.token.set(response.accessToken);
            this.storeCredentials(response.accessToken, response.profile);
            this.toasterService.success('Login success!');
            this.router.navigate(['/']);

        });
     
    }
    storeCredentials(token: string, profile: Profile) {
        localStorage.setItem('profile', JSON.stringify(profile));
        localStorage.setItem('authToken', token);
        this.loggedIn.set(true);
    }
    handleLogout() {
        localStorage.removeItem('authToken');
        localStorage.removeItem('profile');
        this.verifyAuthStorage();
        this.toasterService.warn('Account logged off.');
        this.router.navigate(['/login']);
    }

    handleSignUp(data: SignUpData) {
        this.http.post<AuthResponse>(this.accountApiSignupUrl, data).subscribe(response  => {
            this.profile.set(response.profile);
            this.token.set(response.accessToken);
            this.storeCredentials(response.accessToken, response.profile);
            this.toasterService.success('Welcome to your list!');
            this.router.navigate(['/']);
        });
    }

    genBearerAuthHeader(token: string) {
        return new HttpHeaders({
            Authorization: `Bearer ${token}`
          });
    }
    get isLoggedIn() {
        this.verifyAuthStorage()
        return this.loggedIn();
    }

    get profileData() {
        return this.profile();
    }

    get authToken() {
        return this.token();
    }
  }