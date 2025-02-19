package com.example.newspeed.friend.entity;

import com.example.newspeed.common.entity.BaseEntity;
import com.example.newspeed.friend.status.FriendStatus;
import com.example.newspeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "friend")
@Entity
@NoArgsConstructor
public class Friend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus status = FriendStatus.PENDING;

    public Friend(User requester, User receiver, FriendStatus status) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
    }

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
    }
}
