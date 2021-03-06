package vn.techmaster.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Table(name = "users")  //Để không bị lỗi khi kết nối vào Postgresql
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id private long id;

  @Column(nullable = false, length = 64)
  private String fullname;

  @NaturalId
  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  //Một User viết nhiều Post
  @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  @JoinColumn(name = "user_id")
  private List<Post> posts = new ArrayList<>();
  public void addPost(Post post) {
    posts.add(post);
    post.setUser(this);
  }

  public void removePost(Post post) {
    posts.remove(post);
    post.setUser(null);
  }

  //Một User viết nhiều Post
  @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  @JoinColumn(name = "user_id")
  private List<Bug> bugs = new ArrayList<>();
  public void addBug(Bug bug) {
    bugs.add(bug);
    bug.setUser(this);
  }

  public void removeBug(Bug bug) {
    bugs.remove(bug);
    bug.setUser(null);
  }

  //Một User viết nhiều Comment
  @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  @JoinColumn(name = "user_id")
  private List<Comment> comments = new ArrayList<>();
  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setUser(null);
  }


  // ROLE
  //------
  @ManyToMany
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles = new ArrayList<>();
}
