package com.ssafy.i10a709be.domain.member.entity;

//import com.ssafy.i10a709be.common.entity.BaseEntity;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE member_id = ?")
@SQLRestriction("is_deleted = false")
public class Member {
    @Id
    @UuidGenerator
    private String memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String nickname;

    private String profileImage;

    private String address;

    private String phoneNumber;

    @Column(nullable = false)
    @ColumnDefault("30")
    private Integer score = 30;

    private String refreshToken;

    private Boolean isDeleted = Boolean.FALSE;

    @Builder
    public Member(String email, OAuthProvider provider, String nickname, String profileImage) {
        this.email = email;
        this.provider = provider;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
