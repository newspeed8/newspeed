package com.example.newspeed.user.entity;

import com.example.newspeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    @Column(unique = true)
    private String email;
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateUserName(String userName){
        this.userName = userName;
    }
}
