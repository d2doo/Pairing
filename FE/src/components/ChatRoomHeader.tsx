import { ChatRoomProduct } from "@/types/Chat";
import { Button } from "./ui/button";
import { useNavigate } from "react-router-dom";
interface ChatRoomHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
  product: ChatRoomProduct;
}

function ChatRoomHeader(product: ChatRoomHeaderProps) {
  const navigate = useNavigate();
  return (
    <>
      <div className="flex h-24 w-full items-center justify-start space-x-3 border-b border-black1 px-4">
        <img
          src={product.product.imgSrc}
          alt="/img/extra.png"
          className="size-20 object-cover"
        />
        <div className="w-full flex-col justify-center space-y-1">
          <div className="flex font-[GothicLight] text-sm text-black1">
            {product.product.title}
          </div>
          <div className="flex justify-start space-x-2 text-xs">
            <Button
              onClick={() => {
                navigate(`/product/${product.product.productId}`);
              }}
            >
              상세보기
            </Button>
            <Button
              onClick={(): void => console.log(product.product.productId)}
            >
              구매요청
            </Button>
          </div>
        </div>
      </div>
    </>
  );
}

export default ChatRoomHeader;
