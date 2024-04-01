interface NotificationResponse {
    notificationId: bigint;
    memberId: string;
    content: string;
    isRead: boolean;
    notificationType: string;
    productId: bigint;
}
