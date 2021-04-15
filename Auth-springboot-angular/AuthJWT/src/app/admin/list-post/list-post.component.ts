import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/model/post';
import { User } from 'src/app/model/user';
import { PostService } from 'src/app/_services/post.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-list-post',
  templateUrl: './list-post.component.html',
  styleUrls: ['./list-post.component.scss']
})
export class ListPostComponent implements OnInit {

  user: User = new User();
  posts: Post[];


  constructor(
    private postService: PostService,
    private tokenStoreService: TokenStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = this.tokenStoreService.getUser();

    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
  }

  onCreatePost(){
    this.router.navigate(['create-post']);
  }

  onEditPost(postId: number){
    this.router.navigate(['edit-post', postId]);
  }

  deletePost(postId: number){
    this.postService.deletePostById(postId).subscribe(() => {
      this.gotoAdminPost();
    });
  }

  gotoAdminPost(){
    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
    this.router.navigate(['admin-post']);
  }


}
