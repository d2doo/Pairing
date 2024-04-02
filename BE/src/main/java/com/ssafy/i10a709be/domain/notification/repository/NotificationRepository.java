package com.ssafy.i10a709be.domain.notification.repository;

import com.ssafy.i10a709be.domain.notification.entity.Notification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.member.memberId = :memberId order by n.notificationId desc")
    List<Notification> findNotificationsByMemberId(@Param(value = "memberId") String memberId);
}
