import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { ChatRoomResponse } from "@/types/Chat";
import { useAuthStore } from "@/stores/auth";
import { useLocalAxios } from "@/utils/axios";
import MultiClamp from "react-multi-clamp";
import moment from "moment";

function ChatList() {
  const authMember = useAuthStore();
  const localAxios = useLocalAxios();

  useEffect(() => {
    console.log("auth:", authMember);
    // testChatList();
    loadChatList();
  }, []);

  const [tagList, setTagList] = useState<JSX.Element[]>([]);

  const updateTagList = (tag: JSX.Element) => {
    const nextList: JSX.Element[] = [...tagList, tag];
    setTagList(nextList);
  };

  const loadChatList = async () => {
    const url = `/chat/room`;

    localAxios.get<ChatRoomResponse[]>(url).then((res) => {
      res.data.forEach((element) => {
        let minutesDiff = moment
          .duration(
            moment().diff(
              moment(element.chatResponseDto.createdAt).add(9, "hours"),
            ),
          )
          .asMinutes();

        let displayText;

        if (minutesDiff < 60) {
          // 60분 보다 적으면
          displayText = `${Math.floor(minutesDiff)}분 전`;
        } else {
          // 60분 보다 많으면
          displayText = moment(element.chatResponseDto.createdAt).format(
            "MM월 DD일",
          );
        }

        const tag: JSX.Element = (
          <Link
            to={`/chat/room/${element.chatRoomId}`}
            className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4"
            key={element.chatRoomId}
          >
            <img
              src="/img/extra.png"
              alt="profile_err"
              className="size-20 object-cover"
            />
            <div className="flex-col justify-center">
              <div className="flex space-x-2 font-GothicLight text-sm text-black1">
                <div>{element.title}</div>
                <div className="flex items-center text-xs text-gray1">
                  {displayText}
                </div>
              </div>
              <div className="font-GothicLight text-xs text-gray1">
                <MultiClamp ellipsis="..." clamp={3}>
                  {element.chatResponseDto.content}
                </MultiClamp>
              </div>
            </div>
          </Link>
        );
        console.log(tag);
        updateTagList(tag);
      });
      console.log(tagList);
    });
  };

  return <>{tagList}</>;
}

export default ChatList;
