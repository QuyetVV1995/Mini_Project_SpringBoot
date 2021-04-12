import { Component, OnInit } from '@angular/core';
import { Post } from '../model/post';
import { Tag } from '../model/tag';
import { TagService } from '../_services/tag.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  post: Post = new Post();
  tags: Tag[];
  constructor(
    private tagService: TagService
  ) { }

  ngOnInit(): void {
    this.tagService.getAllTag().subscribe(data => {
      this.tags = data;
    });
  }

  onSubmit(){

  }

}
