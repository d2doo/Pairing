import ChatRoomHeader from "@/components/ChatRoomHeader";
import Chat from "@/components/Chat";
import { ChatRoomProduct } from "@/types/Chat";
import { useState } from "react";
// interface ChatRoomHeaderProps extends React.HTMLAttributes<HTMLDivElement> {
//   product: ChatRoomProduct;
// }
function ChatRoom() {
  const [ product, setProduct ]= useState<ChatRoomProduct>({
    title: '',
    productId: NaN
  })
  const handleProductChange = ( next:ChatRoomProduct) => {
    console.log('child callme', next);
    setProduct((prevProduct) => ({
      ...prevProduct,
      productId: next.productId,
      title: next.title,
    }));
  };


  return (
    
    <>
      <ChatRoomHeader product={product}/>
      <Chat parentHandler={handleProductChange}/>
    </>
  );
}

export default ChatRoom;
