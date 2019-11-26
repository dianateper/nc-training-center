import {Component, OnInit} from '@angular/core';
import {RegView} from '../../models/regview';
import {HttpClient} from '@angular/common/http';
import {Route, Router} from "@angular/router";
import {StorageService} from "../../services/storage/storage.service";
import {User} from "../../models/user";

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
    model: RegView = {
        id: 0,
        login: '',
        password: '',
        email: ''
    };

    constructor(private http: HttpClient,
                private router: Router,
                private storage: StorageService) {
    }

    confirmPassword: string = '';

    ngOnInit() {
    }

    register(): void {
        let url = 'http://localhost:8080/registration';
        let form = new FormData();
        form.append('login', this.model.login);
        form.append('password', this.model.password);
        form.append('email', this.model.email);

        this.http.post<User>(url, form).subscribe(
            res => {
                if (res != null) {
                    // localStorage.setItem('isAdmin', 'false');
                    //localStorage.setItem('currentUser', JSON.stringify(res));
                    //location.assign('/');
                    this.storage.setUser(res);
                    this.router.navigateByUrl('/')
                }
            },
            err => {
                alert(JSON.parse(JSON.stringify(err)).message);
            }
        );
    }
}
