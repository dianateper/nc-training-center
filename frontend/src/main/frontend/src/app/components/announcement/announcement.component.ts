import {ActivatedRoute} from "@angular/router";
import {Component, Input, OnInit} from '@angular/core';
import {Announcement} from '../../models/announcement';
import {HttpClient} from "@angular/common/http";
//import {Storage} from '../../services/storage';
import {StorageService} from "../../services/storage/storage.service";
import {User} from "../../models/user";
@Component({
    selector: 'app-announcement',
    templateUrl: './announcement.component.html',
    styleUrls: ['./announcement.component.css']
})
export class AnnouncementComponent implements OnInit {
    role:String;
    model : Announcement = {
    id :0,
    description: '',
    announcementDate: new Date(),
    bookID : 0,
    priority: 'low',
    ownerId : 0,
    status: 'UNPUBLISHED'

};
    currentUser: User;
    constructor(private http: HttpClient,private route: ActivatedRoute,
                private storage: StorageService) {
    }
    id:any;


    ngOnInit() {
        this.model
        //this.id = parseInt(this.route.snapshot.paramMap.get('id'));
        this.storage
        this.role = 'user';

        this.currentUser = this.storage.getUser();

    }

    createAnnouncement(): void {
        let url = 'http://localhost:8080//announcements//proposeAnnouncement';
        this.model.bookID = this.id;
        if(this.currentUser.userRole == 'admin'){
            this.model.status = 'PUBLISHED';
        }
        this.model.ownerId = this.currentUser.id;
        this.http.post(url, this.model).subscribe(
            res=>{
                //location.reload();
            },
            err=>{
                err => {alert(JSON.parse(JSON.stringify(err)).message);}      }
        );

    }

}
