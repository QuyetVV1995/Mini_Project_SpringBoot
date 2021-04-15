import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from '../model/role';
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

  createAcc(user: User): Observable<Object>{
    return this.http.post(`${this.accountBaseURL}/create`, user);
  }

  getRole(): Observable<Role[]>{
    return this.http.get<Role[]>(`${this.accountBaseURL}/get-roles`);
  }
}
