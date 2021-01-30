package vn.techmaster.blog.DTO;

import java.util.List;

import lombok.Data;
import vn.techmaster.blog.model.Role;

@Data
//UserInfo dùng để trả về dữ liệu phía client, nó không chứa những trường nhạy cảm như password !
public class UserInfo {
  private long id;
  private String fullname;
  private String email;
  private List<Role> roles;
}
