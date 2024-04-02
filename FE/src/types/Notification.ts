import { UnitFind } from "./Unit";

interface NotificationCreateResponse {
    notificationId: bigint;
    memberId: string;
    content: string;
    isRead: boolean;
    notificationType: string;
    productId: bigint;
}

interface NotificationResponse{
    notificationId:bigint
    memberId: string
    content: string
    isRead: boolean
    notificationType:string
    productId: bigint
    units:UnitFind[];
    totalPrice: number
}

export type{ NotificationCreateResponse, NotificationResponse}