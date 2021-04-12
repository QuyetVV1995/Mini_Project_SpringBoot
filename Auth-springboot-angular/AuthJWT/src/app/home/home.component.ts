import { Component, OnInit } from '@angular/core';
import { Post } from '../model/post';
import { PostService } from '../_services/post.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  postList?: Post[];

  constructor(private userService: UserService,
    private postService: PostService
    ) { }

  ngOnInit(): void {
    this.userService.getPublicContent().subscribe(
      data => {
        this.postList = JSON.parse(data) as Post[];
        console.log(data);
      },
      err => {
        this.postList = JSON.parse(err.error).message;
      }
    );
  }
}
