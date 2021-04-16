import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { map, tap } from 'rxjs/operators';
import { Post } from '../model/post';
import { PostData, PostService } from '../_services/post.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  postList?: Post[];
  pageEvent: PageEvent;
  dataSource: PostData = null;
  displayedColums: string[] = ['id', 'title', 'content', 'username', 'create_at'];

  constructor(private userService: UserService,
    private postService: PostService
    ) { }

  ngOnInit(): void {
    this.postService.getPosts(0,9).pipe(
      tap(),
      map((postData: PostData) => this.dataSource = postData)
    ).subscribe(

    );

  }

  onPaginateChange(event: PageEvent){
    let page = event.pageIndex;
    let size = event.pageSize;
    this.postService.getPosts(page, size).pipe(
      tap(),
      map((postData: PostData) => this.dataSource = postData)
    ).subscribe();
  }
}
