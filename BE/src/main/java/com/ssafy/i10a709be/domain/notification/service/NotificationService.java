package com.ssafy.i10a709be.domain.notification.service;

import com.ssafy.i10a709be.domain.notification.entity.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(String memberId);

    void readNotifications(String memberId, List<Long> targetNotifications);
}
