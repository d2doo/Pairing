package com.ssafy.i10a709be.domain.notification.repository;

import com.ssafy.i10a709be.domain.notification.entity.Notification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationsByMember_MemberId(String memberId);
}
