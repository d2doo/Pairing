import { Link } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

interface ChatDto{
  content:string,
  createdAt:Date
}

interface ChatRoomDto{
  chatRoomId:number,
  title:string,
  memberId: string,
  nickname: string,
  capability: number,
  chatResponseDto: ChatDto
}
interface MemberLoginResponseDto{
  memberId:string,
  email:string,
  nickname:string,
  profileImage:string
}

function ChatList() {

  let memberId:string;
  let navigate = useNavigate();
  const OpenChatRoom = ( roomId:number ) => {
      navigate(`/chat/room/${roomId}`, { 'state' : memberId });
  }
  useEffect( () => {
      loadChatList();
  }, [])

  const [ tagList, setTagList ] = useState<JSX.Element[]>([]);
    
  const updateTagList = ( tag:JSX.Element ) => {
      const nextList :JSX.Element[] = [ ...tagList, tag ]
      setTagList( nextList );
  }

  const getLoginTestMemberId = async () => {
      const url = 'http://localhost:8080/member/chat/test/login';
      const value = await axios.get<MemberLoginResponseDto>( url )
      .then( res => {
          let member = res['data'];
          return member['memberId'];
      })

        return value;
    }
    const loadChatList = async () => {
      memberId = await getLoginTestMemberId();
      console.log("memberId:", memberId)
        
      const url = `http://localhost:8080/chat/room?memberId=${memberId}`

      axios.get<ChatRoomDto[]>( url,).then( res => {
          console.log( res['data'])
          res['data'].forEach(element => {
              let tag: JSX.Element =
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
            <div className="flex items-center text-xs text-gray1">{element.chatResponseDto.createdAt.toString()}</div>
          </div>
          <div className="font-GothicLight text-xs text-gray1">
            {element.chatResponseDto.content}
          </div>
        </div>
      </Link>
              console.log( tag );
              updateTagList( tag );
          });
          console.log( tagList );
      })
        
  }

  return (
    <>
      {tagList}
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
