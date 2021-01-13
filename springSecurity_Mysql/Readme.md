Bài viết dựa theo hướng dẫn:
https://www.javaguides.net/2020/06/spring-security-tutorial-with-spring-boot-spring-data-jpa-thymeleaf-and-mysql-database.html

 ##1.Thiết kế cơ sở dữ liệu(MySQL)

Trong project, tạo ra các bảng User, Role và users_roles.
Mỗi user sẽ có nhiều users_role nên có quan hệ một nhiều(1:n).
Mỗi role sẽ có nhiều users_role nên cũng có quan hệ 1:n.
Users_role có 2 khóa ngoại tham chiếu đến 2 bảng User và Role.

![ER_diagram](https://1.bp.blogspot.com/-bPxfSjkLROs/XuXLteDlVvI/AAAAAAAAH2w/1PVKdtwYLzwnr8OHsjKCb_D1QyLilP-aQCK4BGAsYHg/d/ER-diagram.png)

chi tiết xem tại file [demo.sql](demo.sql)

##2.Project Structure
* Generate Structure project use Acsii Tree Generator: https://marketplace.visualstudio.com/items?itemName=aprilandjan.ascii-tree-generator

.
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── demo
│   │               ├── config
│   │               │   └── SecurityConfiguration.java
│   │               ├── model
│   │               │   ├── Role.java
│   │               │   └── User.java
│   │               ├── repository
│   │               │   └── UserRepository.java
│   │               ├── service
│   │               │   ├── UserService.java
│   │               │   └── UserServiceImpl.java
│   │               ├── web
│   │               │   ├── dto
│   │               │   │   └── UserRegistrationDto.java
│   │               │   ├── MainController.java
│   │               │   └── UserRegistrationController.java
│   │               └── DemoApplication.java
│   └── resources
│       ├── static
│       │   └── img
│       │       └── ER_diagram.png
│       ├── templates
│       │   ├── index.html
│       │   ├── login.html
│       │   └── registration.html
│       └── application.properties
└── test
    └── java
        └── com
            └── example
                └── demo
                    └── DemoApplicationTests.java

###2.1 File pom.xml

Trong project sử dụng các dependency
- spring-boot-starter-data-jpa
- mysql-connector-java
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- thymeleaf-extras-springsecurity5
- spring-boot-starter-web
- spring-boot-devtools
- spring-boot-starter-test
- spring-security-test

Chi tiết: [pom.xml](pom.xml)

###2.2 MySQL Database configuration
Trong file application.properties thiết lập kết nối tới Mysql:

`## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
 spring.datasource.url = jdbc:mysql://localhost:3306/demo?useSSL=false
 spring.datasource.username = root
 spring.datasource.password = 12345678`
 
 `## Hibernate Properties`

 `spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect`
 
 `## Hibernate ddl auto`
 
 `spring.jpa.hibernate.ddl-auto = update`
  
  `logging.level.org.hibernate.SQL=DEBUG`
  
  `logging.level.org.hibernate.type=TRACE`
  
  *Trong project kết nối tới MySql địa chỉ localhost:3306, database là demo
  username là root, mật khẩu là 12345678
  
  ###2.3 Model Layer - Create JPA entities
  Giải thích một số đoạn code quan trọng
  ####2.3.1 User
  Trong file [User.java](src/main/java/com/example/demo/model/User.java)
  
  Chú thích:
  
  **@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))**
  
  - Sử dụng @UniqueConstraint để đảm bảo rằng mỗi user chỉ sử dụng 1 email duy nhất, không bị trùng lặp.
  
  **@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)**
  
  @ManytoMany: biểu thị quan hệ N:N trong cơ sở dữ liệu.
  
  Hai cơ chế lấy đối tượng từ database của String JPA:
  - fetch = FetchType.EAGER: khi find, select một đối tượng từ database thì JPA sẽ lấy
  luôn ra cả các đối tượng liên quan phụ thuộc.
  - fetch = FetchType.LAZY: Khi bạn find, select một đối tượng từ database thì JPA sẽ không lấy ra các các đối tượng phụ thuộc,
   chỉ lấy ra các thông tin có trong bảng ứng với entity.
   
  Cascade là một tính năng giúp quản lý trạng thái của các đối tượng trong một mối quan hệ một cách tự động.
  
  Các loại cascade trong JPA (khi sử dụng với EntityManagerFactory/EntityManager)
  
  - ALL: Tương ứng với tất cả các loại cascade. cascade={DETACH, MERGE, PERSIST, REFRESH, REMOVE}
  - DETACH: Nếu đối tượng cha bị detached khỏi persistence context thì các đối tượng tham chiếu tới nó cũng bị detached (tách ra).
  - MERGE: Nếu đối tượng cha được merged vào persistence context, thì các đối tượng tham chiếu tới nó cũng được merged.
  - PERSIST: Nếu đối tượng cha được persisted vào persistence context, thì các đối tượng tham chiếu tới nó cũng được persisted.   
  - REFRESH	Nếu đối tượng cha được refreshed ở persistence context hiện tại, thì các đối tượng tham chiếu tới nó cũng được refreshed.
  - REMOVE	Nếu đối tượng cha bị removed khỏi persistence context, thì các đối tượng tham chiếu tới nó cũng được removed.
  
        @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(
              name = "user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(
              name = "role_id", referencedColumnName = "id"))
              
  @JoinTable: tạo ra một join table có name là users_roles
    trong đó khóa ngoại chính là user_id tham chiếu đến cột id và
    khóa ngoại thứ 2 là role_id tham chiếu đến cột id ???
    
    
 ####2.3.2 Spring Security Configuration
            
          @Override
          protected void configure(HttpSecurity http) throws Exception {
                 http.authorizeRequests().antMatchers(
                         "/registration**",
                         "/js/**",
                         "/css/**",
                         "/img/**").permitAll()
                     .anyRequest().authenticated()
                     .and()
                     .formLogin()
                     .loginPage("/login")
                     .permitAll()
                     .and()
                     .logout()
                     .invalidateHttpSession(true)
                     .clearAuthentication(true)
                     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                     .logoutSuccessUrl("/login?logout")
                     .permitAll();
             }
   
   * Một số cấu hình hay được sử dụng
   - @Configuration: đánh dấu lớp dùng để cấu hình
   - Method configure: cấu hình bảo mật
   - authorizeRequests(): yêu cầu phân quyền request
   - antMatchers(): khai báo đường dẫn request
   - permitAll(): cho phép truy cập tự do không vần xác thực
   - authenticated(): yêu cầu xác thực 
   - anyRequest(): áp dụng cho các request còn lại
   - hasRole(roleName): chỉ cho phép các user có Role truy cập
   - formLogin(): sử dụng form đăng nhập mặc định khi có yêu cầu xác thực
   - defaultSucessUrl(): chuyển hướng tới url này sau khi xác thực thành công
   - failureUrl(): chuyển hướng tới url này khi xác thực thất bại 
   - loginProcessingUrl(): custom url redirect đến khi có yêu cầu xác thực (trong trường hợp không muốn sử dụng form login mặc định)
   