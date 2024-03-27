import { useLocation, useParams } from "react-router-dom";
import { Button } from "./ui/button";
import { Input } from "@/components/ui/input";
import { MemberSummaryResponse } from "../types/Member";
import { ChatMessageResponse, ChatRoomDetail, ChatRoomProduct } from "@/types/Chat";
import React, { ChangeEvent, MutableRefObject, useEffect, useRef, useState } from "react";
import SockJS from 'sockjs-client';
import axios from 'axios';
import * as Stomp from '@stomp/stompjs';

type ParentHandler = (next: ChatRoomProduct) => void;
function Chat( { parentHandler }: { parentHandler: ParentHandler } ){
  let memberId = useRef<string>('');
  const baseUrl = 'https://ssafymain.shop/api'
  const getLoginTestMemberId = async () => {
  
    const url = `${baseUrl}/member/chat/test/login`;
    const value = await axios.get<MemberSummaryResponse>( url )
    .then( res => {

        // console.log( 'res: ',res );
        memberId.current = res.data.memberId;
        return res.data.memberId;
    })

      return value;
  }
  const [ chat, setChat ] = useState<ChatMessageResponse[]>([]);
  const stompClient = useRef<Stomp.Client | null >( null );
  const updateChatting = async (ch:ChatMessageResponse) => {
  setChat((prevChat:ChatMessageResponse[]) => prevChat.concat([ch]));
    
  };

  const tagList:JSX.Element[] = chat.map( (elem) => {
  
    if( elem.memberId == memberId.current ){
      return <div key={elem.chatId} className="chat flex min-h-7 max-w-56 items-center justify-end self-end rounded-lg bg-blue3 px-2 py-1 text-end">
        {elem.content}
      </div>
    }else{
      return <div key={elem.chatId} className="chat flex items-center gap-2">
      <div className="self-start">
        <img
          src="/img/extra.png"
          alt="profile_err"
          className="h-10 w-10 object-cover"
        />
      </div>
      <div className="max-w-56 rounded-lg bg-blue3 px-2 py-1">
        {elem.content}
      </div>
    </div>
    }

  } )
  
  const [ value, setValue ] = useState<string>("");

  const roomId = useParams<string>();
  useEffect( () => {
    getLoginTestMemberId();
    getChatList();
    // console.log("memberId", memberId );
    // console.log('roomId', roomId );
  }, [])

  useEffect( () => {
    scrollToBottom();
  }, [chat]);
  const getChatList = async () =>{

    const chatInfo = await axios.get<ChatRoomDetail>( `${baseUrl}/chat/room/detail/${roomId['roomId']}`);
    parentHandler( chatInfo.data.chatRoomProduct);
    chatInfo.data.chatList.forEach(element => {
      updateChatting( element );
      
    });
    // console.log( 'chat', chat );
    StompSetting();

  }
  const onChangeInputValue = ( e:ChangeEvent<HTMLInputElement> ) => {
    setValue( e.target.value );
  } 

  const StompSetting = () => {

    let socket = new SockJS(`${baseUrl}/ws`);
    stompClient.current = new Stomp.Client({
      brokerURL: `${baseUrl}/ws`, // 이 옵션은 SockJS와 함께 사용할 때 필요 없습니다.
      webSocketFactory: () => socket, // SockJS 소켓을 사용하여 웹소켓을 생성하는 함수를 제공합니다.
      // 연결이 활성화되었을 때 실행할 콜백 함수입니다.
      reconnectDelay: 5000,
      onConnect: (frame) => {
        // console.log("connected", frame);
        const dest = `/chat-room/${roomId.roomId}`;
        // console.log(dest);
        stompClient.current?.subscribe(dest, async (response) => {
          // let obj = JSON.parse(response.body);
          // console.log( response );
          const res =  await JSON.parse( response.body);
          // console.log(res);

          updateChatting(res);
          scrollToBottom();
        });
      },
      
      // 연결에 실패했을 때 실행할 콜백 함수입니다.
      onStompError: (frame) => {
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      }
    });
    stompClient.current.activate();
    // console.log( 'bef stompClient', stompClient );
    
  }


  const send = () => {
    // console.log('보낼 메시지', value);
    // console.log('stompClient', stompClient);
    const dest = `/send/chat/${roomId.roomId}`;
    // console.log( 'sendDEST:' , dest );
   
      stompClient.current?.publish({

        destination: dest,
        body: JSON.stringify({
          roomId:roomId.roomId,
          memberId: memberId.current, // 여기서는 예시로 직접 값을 지정했습니다.
          content: value,
          type: 'message',
          fileId: undefined
        }),
      });

      setValue('');
    
  }
  function scrollToBottom(): void {

    const element = document.getElementById('chatarea');
    // console.log( element?.scrollHeight);
    if (element) {
      element.scrollTop = element.scrollHeight;
    }
  }

  const handleEnter = ( e:React.KeyboardEvent ) => {
    if( e.key=='Enter'){
      send();
    }
  }

  return (
    <>
      {/* 채팅 내용 영역 */}
      <div id="chatarea" className="m-1 h-[calc(100vh-14rem-1rem)] overflow-auto font-GothicLight text-xs">
        <div className="m-1 flex flex-col gap-2">
          {/* <div className="flex min-h-7 max-w-56 items-center justify-end self-end rounded-lg bg-blue3 px-2 py-1 text-end">
            내가 적은 채팅 내용
          </div>

          <div className="flex items-center gap-2">
            <div className="self-start">
              <img
                src="/img/extra.png"
                alt="profile_err"
                className="h-10 w-10 object-cover"
              />
            </div>
            <div className="max-w-56 rounded-lg bg-blue3 px-2 py-1">
              상대방이 적은 채팅 내용 이렇게 흘러넘치면 더 늘어나게 해놨지롱
              룰루랄라 φ(*￣0￣) 상대방이 적은 채팅 내용 이렇게 흘러넘치면 더
              늘어나게 해놨지롱 룰루랄라 φ(*￣0￣)
            </div>
          </div> */}
          {tagList}
        </div>
      </div>

      {/* 입력 영역 */}
      <div className="z-70 fixed bottom-0 left-0 right-0 mb-14">
        <div className="flex items-center justify-evenly p-1">
          <img
            src="/img/add_pic_btn.png"
            alt="add_btn_err"
            className="size-4"
          />
          <div className="flex h-7 w-56 items-center rounded-md border-2 border-black1 p-1 font-Gothic text-xs">
            <Input placeholder="채팅 치는 공간 여기에 있어야함" onChange={onChangeInputValue}  onKeyDown={handleEnter} value={value}></Input>
          </div>
          <Button className="h-7" onClick={ send }>전송</Button>
        </div>
      </div>
    </>
  );
}

export default Chat;
