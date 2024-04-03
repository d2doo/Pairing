import { useParams } from "react-router-dom";
import { Button } from "./ui/button";
import { Input } from "@/components/ui/input";

import {
  ChatMessageResponse,
  ChatRoomDetail,
  ChatRoomProduct,
} from "@/types/Chat";
import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client";
import * as Stomp from "@stomp/stompjs";
import { useAuthStore } from "@/stores/auth.ts";
import { useLocalAxios } from "@/utils/axios";
import moment from "moment";

type ParentHandler = (next: ChatRoomProduct) => void;
function Chat({ parentHandler }: { parentHandler: ParentHandler }) {
  const localAxios = useLocalAxios();
  const authMember = useAuthStore();
  // const baseUrl:string = import.meta.env.VITE_API_BASE_URL;
  const [chat, setChat] = useState<ChatMessageResponse[]>([]);
  const stompClient = useRef<Stomp.Client | null>(null);
  const updateChatting = async (ch: ChatMessageResponse) => {
    setChat((prevChat: ChatMessageResponse[]) => prevChat.concat([ch]));
  };

  useEffect(() => {
    console.log(chat);
  }, [chat]);
  const tagList: JSX.Element[] = chat.map((elem) => {
    if (elem.memberId == authMember.memberId) {
      return (
        <div
          key={elem.chatId}
          className="chat flex min-h-7 max-w-56 items-center justify-end self-end rounded-lg bg-blue3 px-2 py-1 text-end"
        >
          {elem.content}
        </div>
      );
    } else {
      return (
        <div key={elem.chatId} className="chat flex items-center gap-2">
          <div className="self-start">
            {/* 이미지 수정 필요 */}

            <img
              src={elem.profileImage ? elem.profileImage : "/img/extra.png"}
              className="size-11 object-cover"
            />
          </div>
          <div className="flex flex-col gap-1">
            <div className="text-xxs">{elem.nickname}</div>
            <div className="max-w-56 rounded-lg bg-blue3 px-2 py-1">
              {elem.content}
            </div>
          </div>
        </div>
      );
    }
  });

  const [value, setValue] = useState<string>("");

  const roomId = useParams<string>();
  useEffect(() => {
    console.log("authMember:", authMember);
    getChatList();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [chat]);
  const getChatList = async () => {
    const chatInfo = await localAxios.get<ChatRoomDetail>(
      `/chat/room/detail/${roomId["roomId"]}`,
    );
    parentHandler(chatInfo.data.chatRoomProduct);
    chatInfo.data.chatList.forEach((element) => {
      updateChatting(element);
    });
    StompSetting();
  };
  const onChangeInputValue = (e: ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };

  const StompSetting = () => {
    let socket = new SockJS(`${import.meta.env.VITE_API_BASE_URL}/ws`);
    stompClient.current = new Stomp.Client({
      brokerURL: `${import.meta.env.VITE_API_BASE_URL}/ws`, // 이 옵션은 SockJS와 함께 사용할 때 필요 없습니다.
      webSocketFactory: () => socket, // SockJS 소켓을 사용하여 웹소켓을 생성하는 함수를 제공합니다.
      // 연결이 활성화되었을 때 실행할 콜백 함수입니다.
      reconnectDelay: 5000,
      onConnect: () => {
        const dest = `/chat-room/${roomId.roomId}`;
        stompClient.current?.subscribe(dest, async (response) => {
          const res = await JSON.parse(response.body);
          updateChatting(res);
          scrollToBottom();
        });
      },
      onDisconnect: () => {
        socket = new SockJS(`${import.meta.env.VITE_API_BASE_URL}/ws`);
        StompCallback();
      },

      // 연결에 실패했을 때 실행할 콜백 함수입니다.
      onStompError: (frame) => {
        console.log("Broker reported error: " + frame.headers["message"]);
        console.log("Additional details: " + frame.body);
      },
    });
    stompClient.current.activate();
  };

  const StompCallback = () => {
    stompClient.current = new Stomp.Client({
      brokerURL: `${import.meta.env.VITE_API_BASE_URL}/ws`, // 이 옵션은 SockJS와 함께 사용할 때 필요 없습니다.
      webSocketFactory: () => socket, // SockJS 소켓을 사용하여 웹소켓을 생성하는 함수를 제공합니다.
      // 연결이 활성화되었을 때 실행할 콜백 함수입니다.
      reconnectDelay: 5000,
      onConnect: () => {
        const dest = `/chat-room/${roomId.roomId}`;
        stompClient.current?.subscribe(dest, async (response) => {
          const res = await JSON.parse(response.body);
          updateChatting(res);
          scrollToBottom();
        });
      },
    });
  };

  const send = () => {
    const dest = `/send/chat/${roomId.roomId}`;

    stompClient.current?.publish({
      destination: dest,
      body: JSON.stringify({
        roomId: roomId.roomId,
        memberId: authMember.memberId, // 여기서는 예시로 직접 값을 지정했습니다.
        content: value,
        type: "message",
        fileId: undefined,
      }),
    });

    setValue("");
  };
  function scrollToBottom(): void {
    const element = document.getElementById("chatarea");
    if (element) {
      element.scrollTop = element.scrollHeight;
    }
  }

  const handleEnter = (e: React.KeyboardEvent) => {
    if (e.key == "Enter") {
      send();
    }
  };

  return (
    <>
      {/* 채팅 내용 영역 */}
      <div
        id="chatarea"
        className="m-1 h-[calc(100vh-14rem-1rem)] overflow-auto font-GothicLight text-xs"
      >
        <div className="m-1 flex flex-col gap-2">{tagList}</div>
      </div>

      {/* 입력 영역 */}
      <div className="right-0 max-w-96">
        <div className="flex items-center justify-evenly p-1">
          <img
            src="/img/add_pic_btn.png"
            alt="add_btn_err"
            className="size-4"
          />
          <div className="flex h-7 w-56 items-center rounded-md border-2 border-black1 p-1 font-Gothic text-xs">
            <Input
              placeholder="채팅 입력"
              onChange={onChangeInputValue}
              onKeyDown={handleEnter}
              value={value}
              className="border-none focus:border-none"
            ></Input>
          </div>
          <Button className="h-7" onClick={send}>
            전송
          </Button>
        </div>
      </div>
    </>
  );
}

export default Chat;
