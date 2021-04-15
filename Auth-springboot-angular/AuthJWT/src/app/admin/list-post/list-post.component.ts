import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChartDataSets, ChartOptions, ChartScales, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import { Post } from 'src/app/model/post';
import { User } from 'src/app/model/user';
import { AccountService } from 'src/app/_services/account.service';
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
  lengthIndex: number[];

  barChartOptions: ChartOptions = {
    responsive: true,
    scales:{
      yAxes:[{
        ticks: {
          beginAtZero: true
        }
      }]
    }
  };


  barChartLabels: Label[] = [];
  barChartType: ChartType = 'bar';
  barChartLegend = true;
  barChartPlugins = [];

  barChartData: ChartDataSets[] = [
  ];


  constructor(
    private postService: PostService,
    private tokenStoreService: TokenStorageService,
    private router: Router,
    private accService: AccountService
  ) { }

  ngOnInit(): void {
    this.user = this.tokenStoreService.getUser();
    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
    this.chartJS();
  }

  chartJS(){
    // reset label and data Chart
    this.lengthIndex = [];
    this.barChartLabels = [];
    this.accService.getAll().subscribe(dataAcc => {
      dataAcc.forEach(element => {
        this.postService.getAllPostByUserId(element.id).subscribe(dataPost => {
          this.lengthIndex.push(dataPost.length);
          this.barChartLabels.push(element.username);
        });
      });
      this.barChartData = [{
        data: this.lengthIndex,
        label: "Manage Post"
      }];
    });


    console.log(this.barChartLabels);
    console.log(this.lengthIndex);
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
    this.chartJS();
    this.router.navigate(['admin-post']);
  }


}
