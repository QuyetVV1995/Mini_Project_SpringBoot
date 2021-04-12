import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseURL = "http://localhost:8080";

  constructor(
    private http: HttpClient,

  ) { }

  getPostById(postId: number): Observable<Post>{
    return this.http.get<Post>(`${this.baseURL}/post-detail/${postId}`);
  }
}