package com.ssafy.i10a709be.domain.notification.entity;

import com.ssafy.i10a709be.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id @GeneratedValue
    private long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private Boolean isRead;

    public void readNotification() {
        this.isRead = true;
    }
}
