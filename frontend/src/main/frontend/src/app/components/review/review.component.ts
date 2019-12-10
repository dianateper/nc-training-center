import {Component, Input, OnInit} from '@angular/core';
import {Review} from "../../models/review";
import {CommonService} from "../../services/common/common.service";
import {ActivatedRoute} from "@angular/router";
import {Book} from "../../models/book";
import {User} from "../../models/user";
import {UserService} from "../../services/user/user.service";

@Component({
    selector: 'app-review',
    templateUrl: './review.component.html',
    styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {
    // do we need only one review?
    @Input() review: Review;
    @Input() book: Book;
    user: User = new User();

    constructor(private commonService: CommonService, private route: ActivatedRoute,
                private  userService: UserService) {
    }

    ngOnInit() {
        this.getReview();
    }


    getReview(): void {
        const id = +this.route.snapshot.paramMap.get('reviewId');
        this.commonService.getReviewById(id).subscribe(
            review => {
                this.review = new Review();
                this.review = review;
                this.commonService.getBookById(review.bookId).subscribe(
                    book => {
                        this.book = book
                    });
                this.userService.searchUser(review.userId.toString()).subscribe(
                    user => this.user = user
                );

            }
        );
    }

    fillArray(grade: number) {
        return Array.from({length: grade}, (v, i) => i)
    }
}
