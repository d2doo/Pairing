import { useNavigate } from "react-router-dom";
import BackBtn from "@/assets/images/back-btn.png";
import Notification from "./Notification";
import { IoChatboxEllipses } from "react-icons/io5";
import { GiConfirmed } from "react-icons/gi";
interface Props {
  title: string;
  prev: string;
}
interface NotificationProps {
  openhandler: (notificationId: number) => void;
  closehandler: (notificationId: number) => void;
  notification: NotificationResponse;
}
import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import * as Stomp from "@stomp/stompjs";
import { useAuthStore } from "@/stores/auth";
import { useLocalAxios } from "@/utils/axios";

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { NotificationResponse } from "@/types/Notification";

import { BellRing, Check } from "lucide-react";

import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Label } from "@radix-ui/react-label";

const Header: React.FC<Props> = ({ title, prev }) => {
  const navigate = useNavigate();
  const [notificationCount, setNotificationCount] = useState<number>(0);
  const auth = useAuthStore();
  const stompClient = useRef<Stomp.Client | null>(null);
  // const notificationList = useRef<NotificationResponse[]>([]);
  const [notificationList, setNotificationList] = useState<
    NotificationResponse[]
  >([]);
  const localAxios = useLocalAxios();
  const baseUrl: string = import.meta.env.VITE_API_BASE_URL;
  const send = () => {
    const dest = `/send/notification`;

    stompClient.current?.publish({
      destination: dest,
      body: JSON.stringify({
        topicSubject: "product",
        members: [auth.memberId],
        content: "테스트 알림 입니다.",
        isRead: false,
        notificationType: "confirm",
        productId: 4,
      }),
    });
  };

  const readAllNotifications = async () => {
    await localAxios.put(
      `/notification`,
      notificationList.map((notification) => notification.notificationId),
    );
    notificationList.forEach((e) => (e.isRead = true));
    changeNotificationCount();
  };

  const changeNotificationCount = () => {
    const notificationCount = notificationList.reduce((count, item) => {
      return item.isRead === false ? count + 1 : count;
    }, 0);
    setNotificationCount(notificationCount);
  };
  useEffect(() => {
    //헤더 불러올 때마다 초기화
    setNotificationList([]);
    stompSetting();
  }, []);
  const stompSetting = () => {
    const socket = new SockJS(`${baseUrl}/ws`);
    stompClient.current = new Stomp.Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: (frame) => {
        // console.log(`connected ${auth.memberId}`, frame);
        const dest = `/product-notification/${auth.memberId}`;

        stompClient.current?.subscribe(dest, async (response) => {
          const res = await JSON.parse(response.body);
          setNotificationList((prev) => [res, ...prev]);
        });
      },
      onDisconnect: (frame) => stompCallback(),
    });
    stompClient.current?.activate();

    getNotifications();
  };
  const stompCallback = () => {
    const socket = new SockJS(`${baseUrl}/ws`);
    stompClient.current = new Stomp.Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: (frame) => {
        // console.log(`connected ${auth.memberId}`, frame);
        const dest = `/product-notification/${auth.memberId}`;
        stompClient.current?.subscribe(dest, async (response) => {
          const res = await JSON.parse(response.body);
          setNotificationList((prev) => [res, ...prev]);
        });
      },
      onDisconnect: (frame) => stompCallback(),
    });
    stompClient.current?.activate();
  };

  const getNotifications = async () => {
    const res = await localAxios.get(`/notification`, {
      params: {
        memberId: auth.memberId,
      },
    });
    setNotificationList((prev) => prev.concat(res.data));
  };

  const onClickReadListener = async (notificationId: bigint) => {
    const findNotification = notificationList.find(
      (n) => n.notificationId === notificationId,
    );
    await localAxios.put(
      `/notification`,
      notificationList.map((notification) => notification.notificationId),
    );
    if (findNotification?.notificationType == "confirm") {
      localAxios.post(`/deal/confirm/${findNotification.productId}`);
    }
  };

  const onClickCloseListener = (notificationId: bigint) => {
    const findNotification = notificationList.find(
      (n) => n.notificationId === notificationId,
    );
    if (!findNotification || findNotification?.notificationType == "message")
      return;

    localAxios.delete(`/deal/confirm/${findNotification.productId}`);
  };

  useEffect(() => {
    changeNotificationCount();
  }, [notificationList]);

  return (
    <div className="flex h-12 items-center justify-between border-b border-gray1 px-4 font-GothicMedium text-black">
      <div className="flex h-12 items-center">
        {prev && (
          <button
            onClick={() => {
              navigate(prev);
            }}
            className="mr-3"
          >
            <img src={BackBtn} alt="뒤로 가기" className="h-4 w-auto" />
          </button>
        )}
        <p className="flex items-center">{title}</p>
      </div>
      <div className="flex h-12 items-center pt-1.5">
        <Popover>
          <PopoverTrigger>
            <p id="bell" className="material-symbols-outlined">
              notifications
            </p>
          </PopoverTrigger>
          <PopoverContent className={cn("w-72", "h-100", "m-1")}>
            <Card className={cn("w-100", "h-100")}>
              <CardHeader>
                <CardTitle>알림</CardTitle>
                <CardDescription>
                  {notificationCount}개의 안 읽은 알림이 있습니다.
                </CardDescription>
              </CardHeader>
              <CardContent className="grid h-2/3 gap-4 overflow-y-scroll">
                <div className="items-center rounded-md border bg-background p-2 text-xs hover:bg-accent hover:text-accent-foreground ">
                  <div className="flex-1 space-y-1">
                    <p className="text-muted-foreground">
                      제품 매칭 및 채팅에 대한 내용들을 담고 있습니다.
                    </p>
                  </div>
                </div>
                <div className="h-full overflow-scroll">
                  {notificationList.map((notification) => (
                    <div
                      key={notification.notificationId}
                      className="mb-4 grid grid-cols-[25px_1fr] items-start pb-4 last:mb-0 last:pb-0"
                    >
                      {notification.notificationType == "confirm" ? (
                        <GiConfirmed />
                      ) : (
                        <IoChatboxEllipses />
                      )}
                      <div className="space-y-1">
                        <p className="text-xs font-medium leading-none">
                          {notification.content}
                        </p>
                        <p className="text-xs text-muted-foreground">
                          <Notification
                            key={notification.notificationId}
                            openhandler={onClickReadListener}
                            closehandler={onClickCloseListener}
                            notification={notification}
                          />
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              </CardContent>
              <CardFooter className="flex justify-center">
                <Button className="h-8 w-1/2" onClick={readAllNotifications}>
                  전부 확인하기
                </Button>
              </CardFooter>
            </Card>
          </PopoverContent>
        </Popover>
        <div className="relative">
          <Label htmlFor="bell" className="absolute right-0 top-0">
            {notificationCount > 0 && (
              <div className="flex h-4 w-4 items-center justify-center rounded-full bg-red-500">
                <p className="text-xs text-white">{notificationCount}</p>
              </div>
            )}
          </Label>
        </div>
      </div>
    </div>
  );
};

export default Header;
