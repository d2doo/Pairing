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

    // testChatList();
    loadChatList();
  }, [location.pathname]);

  // const testChatList = () => {
  //   const productDto: ProductSaveRequest = {
  //     productTitle: "My Product",
  //     unit: {
  //       categoryId: 1,
  //       isCombinable: true,
  //       unitDescription: "temp",
  //       price: 1000,
  //       age: 1,
  //       images: [],
  //       status: "상태 좋ㅇ므",
  //       partTypeIds: [1],
  //     },
  //     targetUnits: [1, 3], // 예시로 넣은 값
  //     thumbnailIndex: 0, // 예시로 넣은 값
  //   };
  //   localAxios.post(`/product`, productDto).then(() => {
  //     loadChatList();
  //   });
  // };

  // const [tagList, setTagList] = useState<JSX.Element[]>([]);
  const [chatList, setChatList] = useState<ChatRoomResponse[]>([]);

  const loadChatList = async () => {
    const url = `/chat/room`;

    const chatrooms = await localAxios.get<ChatRoomResponse[]>(url);
    console.log(chatrooms);
    setChatList(chatrooms.data);
  };

  return (
    <>
      {chatList.map((element) => (
        <Link
          to={`/chat/room/${element.chatRoomId}`}
          className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4"
          key={element.chatRoomId}
        >
          <img
            src={element.imgSrc}
            alt="profile_err"
            className="size-20 object-cover"
          />
          <div className="flex-col justify-center">
            <div className="flex space-x-2 font-GothicLight text-sm text-black1">
              <div>{element.title}</div>
              <div className="flex items-center text-xs text-gray1">
                {element.chatResponseDto.createdAt.toString()}
              </div>
            </div>
            <div className="font-GothicLight text-xs text-gray1">
              {element.chatResponseDto.content}
            </div>
          </div>
        </Link>
      ))}

      {/* <Link
        to="/chat/room"
        className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4"
      >
        {/* 임시 이미지 -> 후에 결합 어떻게 할건지도 생각해봐야함. */}
      {/* <img
          src="/img/extra.png"
          alt="profile_err"
          className="size-20 object-cover"
        />
        <div className="flex-col justify-center">
          <div className="flex space-x-2 font-GothicLight text-sm text-black1">
            <div>제목 컨테이너</div>
            <div className="flex items-center text-xs text-gray1">대화날짜</div>
          </div>
          <div className="font-GothicLight text-xs text-gray1">
            마지막 대화내용 여기 나와야 돼ㅐㅐㅐㅐ
          </div>
        </div> 
      </Link> */}
    </>
  );
}

export default ChatList;
