package vn.techmaster.blog.controller.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentRequest {
 // private long user_id; //Author of comment
  // private long post_id; //Post that comment targets to
  private long bug_id; //Post that comment targets to
  private String content;

  public CommentRequest(long bug_id) {
    this.bug_id = bug_id;
  }
}