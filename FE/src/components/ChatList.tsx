import { Link } from "react-router-dom";

function ChatList() {
  return (
    <>
      <Link
        to="/chat/room"
        className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4"
      >
        {/* 임시 이미지 -> 후에 결합 어떻게 할건지도 생각해봐야함. */}
        <img
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
      </Link>
    </>
  );
}

export default ChatList;
