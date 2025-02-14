package com.example.newspeed.post.entity;

import com.example.newspeed.common.entity.BaseEntity;
import com.example.newspeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "id")
    private User userId;

    @Column(nullable = false)
    private String nickname2;

    public Post(String title, String content, String imageUrl, String nickname2) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.nickname2 = nickname2;
    }


    public void update(String title, String content, String imageUrl, String nickname2) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.nickname2 = nickname2;
    }
}
