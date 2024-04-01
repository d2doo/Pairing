import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import * as Stomp from "@stomp/stompjs";
import { useAuthStore } from "@/stores/auth";
import { useLocalAxios } from "@/utils/axios";

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

function Notification() {
  const [notificationCount, setNotificationCount] = useState<number>(0);
  const auth = useAuthStore();
  const stompClient = useRef<Stomp.Client | null>(null);
  const notificationList = useRef<NotificationResponse[]>([]);
  const localAxios = useLocalAxios();
  const baseUrl: string = import.meta.env.VITE_API_BASE_URL;

  const readNotifications = async () => {
    console.log("Read Messages");
    await localAxios.put(
      `/notification`,
      notificationList.current.map(
        (notification) => notification.notificationId,
      ),
    );
    getNotifications();
    changeNotificationCount();
  };

  const changeNotificationCount = () => {
    const notificationCount = notificationList.current.reduce((count, item) => {
      return item.isRead === false ? count + 1 : count;
    }, 0);
    setNotificationCount(notificationCount);
  };

  const stompSetting = () => {
    const socket = new SockJS(`${baseUrl}/ws`);
    stompClient.current = new Stomp.Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: (frame) => {
        console.log(`connected ${auth.memberId}`, frame);
        const dest = `/product-notification/${auth.memberId}`;
        stompClient.current?.subscribe(dest, async () => {
          getNotifications();

          const notificationCount = notificationList.current.reduce(
            (count, item) => {
              return item.isRead === false ? count + 1 : count;
            },
            0,
          );
          setNotificationCount(notificationCount);
        });
      },
    });
    stompClient.current?.activate();
  };

  const getNotifications = async () => {
    const res = await localAxios.get(`/notification`, {
      params: {
        memberId: auth.memberId,
      },
    });
    notificationList.current = [];
    res.data.map((item: NotificationResponse) => {
      notificationList.current.push(item);
    });
    changeNotificationCount();
  };

  useEffect(() => {
    if (auth.memberId) {
      stompSetting();
      getNotifications();
    }
  }, [auth]);

  return (
    <>
      <div className="flex h-12 w-full items-center justify-between border-b font-GothicMedium text-base text-black1">
        <div className="flex justify-end">
          <p
            className="material-symbols-outlined"
            onClick={() => {
              const messages = notificationList.current
                .map(
                  (notification, index) =>
                    `Notification ${index + 1}: ${notification.content}`,
                )
                .join("\n");

              alert(messages);
              readNotifications();
            }}
          >
            notifications
          </p>
          {notificationCount > 0 && (
            <div className="flex h-4 w-4 items-center justify-center rounded-full bg-red-500">
              <p className="text-xs text-white">{notificationCount}</p>
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default Notification;
