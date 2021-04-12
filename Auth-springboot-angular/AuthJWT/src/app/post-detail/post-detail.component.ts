import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment } from '../model/comment';
import { Post } from '../model/post';
import { User } from '../model/user';
import { CommentService } from '../_services/comment.service';
import { PostService } from '../_services/post.service';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {

  id: number;
  post: Post = new Post();
  user: User = new User();
  isLogin = false;
  comments: Comment[];
  newComment: Comment = new Comment();

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private tokenStoreService: TokenStorageService,
    private commentService: CommentService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.postService.getPostById(this.id).subscribe(data => {
      this.post = data;
      
    });
    this.user = this.tokenStoreService.getUser();
    this.isLogin = !!this.user.id;
  }

  onSubmit(postId: number){
    this.saveComment(postId);
  }

  private saveComment(postId: number){
    this.commentService.createComment(this.newComment, postId, this.user.id).subscribe(data => {
      this.gotoPostDetail(postId);
      console.log('gotopostdetail');

    }),
    error => console.log(error);
  }

  gotoPostDetail(postId: number){
    this.router.navigate(['/post-detail', postId]);
  }

}
