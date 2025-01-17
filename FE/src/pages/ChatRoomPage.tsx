import ChatRoomHeader from "@/components/ChatRoomHeader";
import Chat from "@/components/Chat";
import { ChatRoomProduct } from "@/types/Chat";
import { useState } from "react";

// interface ChatRoomHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
//   product: ChatRoomProduct;
// }
function ChatRoom() {
  const [product, setProduct] = useState<ChatRoomProduct>({
    title: "",
    imgSrc: "",
    productId: NaN,
  });
  const handleProductChange = (next: ChatRoomProduct) => {
    setProduct((prevProduct) => ({
      ...prevProduct,
      productId: next.productId,
      title: next.title,
      imgSrc: next.imgSrc,
    }));
  };

  return (
    <>
      <ChatRoomHeader product={product} />
      <Chat parentHandler={handleProductChange} />
    </>
  );
}

export default ChatRoom;
