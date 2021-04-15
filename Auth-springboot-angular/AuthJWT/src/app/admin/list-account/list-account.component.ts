import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user';
import { AccountService } from 'src/app/_services/account.service';

@Component({
  selector: 'app-list-account',
  templateUrl: './list-account.component.html',
  styleUrls: ['./list-account.component.scss']
})
export class ListAccountComponent implements OnInit {

  accounts: User[];

  constructor(
    private accountService: AccountService
  ) { }

  ngOnInit(): void {
    this.accountService.getAll().subscribe(data => {
      this.accounts = data;
      console.log(data);
    });
  }

  onCreateAccount(){

  }

}
