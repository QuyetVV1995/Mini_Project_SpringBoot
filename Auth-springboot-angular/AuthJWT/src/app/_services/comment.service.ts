import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private createCommentURL = 'http://localhost:8080/new-comment';
  private deteleCommentURL = 'http://localhost:8080/comment/delete';

  constructor(
    private http: HttpClient,
  ) { }

  createComment(cmt: Comment, postId: number, userId: number): Observable<Object>{
    return this.http.post(`${this.createCommentURL}/${postId}/${userId}`, cmt);
  }

  deleteComment(commentId: number, postId: number): Observable<Object>{
    return this.http.delete(`${this.deteleCommentURL}/${postId}/${commentId}`);
  }
}
