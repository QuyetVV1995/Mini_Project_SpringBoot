
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Comment } from '../model/comment';

import { Post } from '../model/post';
import { Tag } from '../model/tag';
import { User } from '../model/user';
import { PostService } from '../_services/post.service';
import { TagService } from '../_services/tag.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { from } from 'rxjs';
import { Role } from '../model/role';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  post: Post = new Post();
  tags: Tag[];
  user: User;
  private roles: string[] = [];

  constructor(
    private tagService: TagService,
    private tokenStoreService: TokenStorageService,
    private postService: PostService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.tagService.getAllTag().subscribe(data => {
      this.tags = data;
    });
    this.post.tag = [];

    this.user = this.tokenStoreService.getUser();
    this.roles.push(this.user.roles.name);
  }

  onSubmit(){
    this.post.user = this.user;
    this.post.create_at = new Date();
    this.createPost();
  }

  onChange(tagCheck: Tag){
    this.post.tag.push(tagCheck);
  }

  createPost(){
    this.postService.createPost(this.post).subscribe(() => {
      if(this.roles.includes('ROLE_WRITER')){
        this.gotoManagePost();
      }   
    });
  }

  gotoManagePost(){
    this.router.navigate(['post']);
  }

  gotoAdminManagePost(){
    this.router.navigate(['admin-post']);
  }

}
