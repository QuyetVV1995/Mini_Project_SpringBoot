import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  users = [];
  page = 0;
  maximumPages = 3;
  constructor(public navCtrl: NavController, private httpClient: HttpClient) {
    this.loadUsers();
  }

  loadUsers(infiniteScroll?) {
    this.httpClient.get(`https://randomuser.me/api/?results=20&page=${this.page}`)
    .subscribe(res => {
      this.users = this.users.concat(res['results']);
      if (infiniteScroll) {
        infiniteScroll.target.complete();
      }
    })
  }

  loadMore(infiniteScroll) {
    this.page++;
    this.loadUsers(infiniteScroll);

    if (this.page === this.maximumPages) {
      infiniteScroll.target.disabled = true;
    }
  }

}
