import {Injectable} from '@angular/core';
import {User} from "../../models/user";
import {BehaviorSubject, Observable} from "rxjs";
import {BookFilter} from "../../models/bookfilter";

@Injectable({
    providedIn: 'root'
})
export class StorageService {
    public currentUser: Observable<User>;
    public currentFilter: Observable<BookFilter>;
    private currentUserSubject: BehaviorSubject<User>;
    private currentFilterSubject: BehaviorSubject<BookFilter>;

    constructor() {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem('user')));
        this.currentUser = this.currentUserSubject.asObservable();

        this.currentFilterSubject = new BehaviorSubject<BookFilter>({id: 0, header: "", author: [], genre: []});
        this.currentFilter = this.currentFilterSubject.asObservable();
    }

    getUser(): User {
        return this.currentUserSubject.getValue();
    }

    setUser(value: User) {
        this.currentUserSubject.next(value);
    }

    setFilter(value: BookFilter) {
        this.currentFilterSubject.next(value);
    }

    getFilter() {
        return this.currentFilterSubject.getValue();
    }
}
