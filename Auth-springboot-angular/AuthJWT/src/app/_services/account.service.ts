import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private accountBaseURL = 'http://localhost:8080/account';
  constructor(
    private http: HttpClient
  ) { }

  getAll(): Observable<User[]>{
    return this.http.get<User[]>(`${this.accountBaseURL}/all`);
  }
}
