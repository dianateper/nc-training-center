import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {StorageService} from "../storage/storage.service";
import {User} from "../../models/user";

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
     private siteUrl: string = 'https://nc-group1-2019.herokuapp.com';
  //  private siteUrl: string = 'http://localhost:8080';

    constructor(private http: HttpClient,
                private storageService: StorageService) {
    }

    public login(username: string, password: string) {
        let form = new FormData();
        form.append('login', username);
        form.append('password', password);

        return this.http.post<User>(`${this.siteUrl}/login` + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token, form);
    }

    logout() {
        window.sessionStorage.removeItem('token');
        window.sessionStorage.removeItem('user');
        this.storageService.setUser(null);
    }

    register(login: string, password: string, email: string) {
        let url = `${this.siteUrl}/registration`;
        let form = new FormData();
        form.append('login', login);
        form.append('password', password);
        form.append('email', email);

        return this.http.post<User>(url, form);
    }

    confirmEmail(email: string, code: string) {
        let url = `${this.siteUrl}/user/activate` + '?access_token=' + JSON.parse(window.sessionStorage.getItem('token')).access_token;
        ;
        let form = new FormData();
        form.append('email', email);
        form.append('code', code);
        return this.http.post<User>(url, form);
    }

    resendCode(email: string) {
        let url = `${this.siteUrl}/email`;
        let form = new FormData();
        form.append('email', email);
        return this.http.post(url, form);
    }

    checkPasswordForRegexp(password: string): boolean {
        let regexp = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!^%#*?&])[A-Za-z\\d@$#!%^*?&]{8,}$');
        return regexp.test(password);
    }
}
