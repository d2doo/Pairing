import { Button } from "./ui/button";

function ChatRoomHeader() {
  return (
    <>
      <div className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4">
        <img
          src="/img/extra.png"
          alt="profile_err"
          className="size-20 object-cover"
        />
        <div className="w-full flex-col justify-center space-y-1">
          <div className="flex font-[GothicLight] text-sm text-black1">
            상품 게시글 제목입니다용
          </div>
          <div className="flex justify-start space-x-2 text-xs">
            <Button>상세보기</Button>
            <Button>구매요청</Button>
          </div>
        </div>
      </div>
    </>
  );
}

export default ChatRoomHeader;
