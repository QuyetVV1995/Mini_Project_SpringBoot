package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "post")
@Table(name = "post")
@Indexed //Thêm annotation này báo cho Hibernate Search đánh chỉ mục bảng này
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField //Đánh chỉ mục full text
    private String title;

    @FullTextField //Đánh chỉ mục full text
    @Column(length=5000)
    private String content;

    private LocalDate create_at;

    public Post(String title, String content, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    //-------
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
    //------
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;  //Tác giả viết post

    //------------
    //Quan hệ nhiều nhiều:
    //- Một post được phân loại bởi 1 hay nhiều tag.
    //- Ngược lại mỗi tag dùng để phân loại nhiều post

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comments) {
        this.comments.add(comments);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Post() {
    }

    public Post(Long id, String title, String content, LocalDate create_at, List<Comment> comments, User user, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_at = create_at;
        this.comments = comments;
        this.user = user;
        this.tags = tags;
    }
}