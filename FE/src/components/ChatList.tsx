import { Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { ChatRoomResponse } from "@/types/Chat";
import { useAuthStore } from "@/stores/auth";
import { useLocalAxios } from "@/utils/axios";
import MultiClamp from "react-multi-clamp";
import moment from "moment";

function ChatList() {
  const authMember = useAuthStore();
  const localAxios = useLocalAxios();
  const location = useLocation();
  useEffect(() => {
    console.log("auth:", authMember);
    loadChatList();
  }, [location.pathname]);

  // const [tagList, setTagList] = useState<JSX.Element[]>([]);
  const [chatList, setChatList] = useState<ChatRoomResponse[]>([]);

  const loadChatList = async () => {
    const url = `/chat/room`;

    const chatrooms = await localAxios.get<ChatRoomResponse[]>(url);
    console.log(chatrooms);
    setChatList(chatrooms.data);
  };

  const formatDate = (createdAt) => {
    let minutesDiff = moment
      .duration(moment().diff(moment(createdAt)))
      .asMinutes();
    if (minutesDiff < 60) {
      return `${Math.floor(minutesDiff)}분 전`;
    } else {
      return moment(createdAt).format("MM월 DD일");
    }
  };

  return (
    <>
      {chatList.map((element) => (
        <Link
          to={`/chat/room/${element.chatRoomId}`}
          className="flex h-24 w-full items-center justify-start space-x-3 border-b border-gray1 px-4"
          key={element.chatRoomId}
        >
          <img
            src={element.imgSrc}
            alt="profile_err"
            className="size-20 object-cover"
          />
          <div className="flex-col justify-center">
            <div className="flex space-x-2 font-GothicLight text-xs text-black1">
              <div>{element.title}</div>
              <div className="flex items-center text-xxs text-gray1">
                {formatDate(element.chatResponseDto.createdAt)}
              </div>
            </div>
            <MultiClamp ellipsis="..." clamp={3}>
              <div className="font-GothicLight text-xs text-gray1">
                {element.chatResponseDto.content}
              </div>
            </MultiClamp>
          </div>
        </Link>
      ))}
    </>
  );
}

export default ChatList;
