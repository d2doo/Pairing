import { Link } from "react-router-dom";

function ChatList() {
  return (
    <>
      <Link
        to="/chat/room"
        className="flex justify-start items-center space-x-3 w-full h-24 border-b border-black1 px-4"
      >
        {/* 임시 이미지 -> 후에 결합 어떻게 할건지도 생각해봐야함. */}
        <img
          src="/img/extra.png"
          alt="profile_err"
          className="object-cover size-20"
        />
        <div className="flex-col justify-center">
          <div className="flex space-x-2 text-black1 text-sm font-GothicLight">
            <div>제목 컨테이너</div>
            <div className="text-gray1 text-xs flex items-center">대화날짜</div>
          </div>
          <div className="text-gray1 text-xs font-GothicLight">
            마지막 대화내용 여기 나와야 돼ㅐㅐㅐㅐ
          </div>
        </div>
      </Link>
    </>
  );
}

export default ChatList;
