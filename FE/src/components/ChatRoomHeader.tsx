import { Button } from "./ui/button";

function ChatRoomHeader() {
  return (
    <>
      <div className="flex justify-start items-center space-x-3 w-full h-24 border-b border-black1 px-4">
        <img
          src="/img/extra.png"
          alt="profile_err"
          className="object-cover size-20"
        />
        <div className="w-full flex-col space-y-1 justify-center">
          <div className="flex text-black1 text-sm font-[GothicLight]">
            상품 게시글 제목입니다용
          </div>
          <div className="flex space-x-2 text-xs justify-start">
            <Button>상세보기</Button>
            <Button>구매요청</Button>
          </div>
        </div>
      </div>
    </>
  );
}

export default ChatRoomHeader;
