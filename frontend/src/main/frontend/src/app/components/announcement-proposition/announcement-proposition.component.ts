import {Component, OnInit} from '@angular/core';
import {Announcement} from "../../models/announcement";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../services/storage/storage.service";
import {User} from "../../models/user";
import {CommonService} from "../../services/common/common.service";

@Component({
    selector: 'app-announcement-proposition',
    templateUrl: './announcement-proposition.component.html',
    styleUrls: ['./announcement-proposition.component.css']
})
export class AnnouncementPropositionComponent implements OnInit {

    private siteUrl: string = 'https://nc-group1-2019.herokuapp.com';

    announcements: Announcement[] = [];
    currentUser: User;


    constructor(private http: HttpClient,private route: ActivatedRoute  , private storage: StorageService,private apiService :CommonService) {
    }

    ngOnInit() {
        this.getAllAnnouncement();
        this.storage
        this.currentUser = this.storage.getUser();
    }

    public getAllAnnouncement() {
        let url = `${this.siteUrl}/announcements/new`;
        this.apiService.getAnnouncementsUnPublish().subscribe(
            res => {
                this.announcements = res;
            },
            err => {
                alert("Error in getting all announcements");
            }
        )
    }
    public publishAnnouncement(announcement : Announcement){
        let url = 'http://localhost:8080/announcements/publish';

        //announcement.admin_id = this.storage.getUser().id;
        this.apiService.publishAnnouncement(announcement).subscribe(
            res=>{
                //location.reload();
            },
            err => {
                alert(JSON.parse(JSON.stringify(err)).message);
            }
        );
    }
}
