package com.ssafy.i10a709be.domain.notification.service;

import com.ssafy.i10a709be.domain.notification.entity.Notification;
import com.ssafy.i10a709be.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification> getNotifications(String memberId) {
        return notificationRepository.findNotificationsByMember_MemberId(memberId);
    }

    @Override
    @Transactional
    public void readNotifications(String memberId, List<Long> targetNotifications) {
        log.info("MemberID : " + memberId);
        List<Notification> notifications = notificationRepository.findNotificationsByMember_MemberId(memberId);
        HashMap<Long, Notification> notificationHashMap = new HashMap<>();
        for (Notification notification : notifications) {
            notificationHashMap.put(notification.getNotificationId(), notification);
        }
        for (long notificationId : targetNotifications) {
            if (!notificationHashMap.containsKey(notificationId)) {
                throw new IllegalArgumentException("접근 권한 에러. 자신의 알림이 아닙니다.");
            }
        }
        for (long notificationId : targetNotifications) {
            Notification notification = notificationHashMap.get(notificationId);
            notification.readNotification();
        }
    }
}
