import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { PostDetailComponent } from './post-detail/post-detail.component';
import { CreatePostComponent } from './create-post/create-post.component';
// import { EditPostComponent } from './edit-post/edit-post.component';
// import { AdminPostComponent } from './admin/post/post.component';
// import { AccountComponent } from './admin/account/account.component';
import { ChartsModule } from 'ng2-charts';
// import { AccountCreateComponent } from './admin/account/create/create.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { EditPostComponent } from './edit-post/edit-post.component'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    PostDetailComponent,
    CreatePostComponent,
    CreatePostComponent,
    EditPostComponent,
    // AdminPostComponent,
    // AccountComponent,
    // AccountCreateComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule, ChartsModule, FontAwesomeModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
