import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseURL = "http://localhost:8080/post";

  constructor(
    private http: HttpClient,

  ) { }

  getPostById(postId: number): Observable<Post>{
    return this.http.get<Post>(`${this.baseURL}/detail/${postId}`);
  }

  getAllPostByUserId(userId: number): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.baseURL}/allPost/${userId}`);
  }

  createPost(post: Post): Observable<Object>{
    console.log(post);
    return this.http.post(`${this.baseURL}/create`,  {
      title: post.title,
      content: post.content,
      create_at: post.create_at,
      tags: post.tag,
      user: post.user
    });
  }

  updatePostById(postId: number, post: Post): Observable<Object>{
    return this.http.put(`${this.baseURL}/edit/${postId}`, {
      title: post.title,
      content: post.content,
      tags: post.tag
    });
  }

  deletePostById(postId: number): Observable<Object>{
    return this.http.delete(`${this.baseURL}/delete/${postId}`);
  }

  getPosts(): Observable<Post[]>{
    return this.http.get<Post[]>(`${this.baseURL}/all`);
  }

}
