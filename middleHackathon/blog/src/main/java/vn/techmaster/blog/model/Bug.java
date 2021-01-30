package vn.techmaster.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bug")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bug {

    public enum status {
        FIXED, NOT_FIX, ESCALATED;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length=5000)
    private String content;

    @Enumerated(EnumType.STRING)
    private status status;


    //-------
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "bug_id")
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBug(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBug(null);
    }

    //------
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;  //Tác giả viết post
    
    @Lob
    private byte[] photo;

    private String namefile;
    private String namefile1;
    private String namefile2;
}
