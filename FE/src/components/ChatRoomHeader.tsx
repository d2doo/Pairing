import { ChatRoomProduct } from "@/types/Chat";
import { Button } from "./ui/button";
interface ChatRoomHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
  product: ChatRoomProduct
}

function ChatRoomHeader( product:ChatRoomHeaderProps) {
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
            {product.product.title}
          </div>
          <div className="flex justify-start space-x-2 text-xs">
            <Button onClick={():void => console.log( product.product.productId)}>상세보기</Button>
            <Button onClick={ ():void => console.log( product.product.productId)}>구매요청</Button>
          </div>
        </div>
      </div>
    </>
  );
}

export default ChatRoomHeader;
