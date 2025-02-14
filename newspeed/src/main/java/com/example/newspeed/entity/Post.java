package com.example.newspeed.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
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
    private String image_url;

    /*
       @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
       private User user_id;
    */
    @Column(nullable = false)
    private String nickname2;

    public Post(String title, String content, String image_url, String nickname2) {
        this.title = title;
        this.content = content;
        this.image_url = image_url;
        this.nickname2 = nickname2;
    }

}
