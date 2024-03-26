import { MemberSummaryResponse } from "./Member";

interface ChatResponse{
    content:string,
    createdAt:Date
  }
  
interface ChatRoomResponse{
    chatRoomId:number,
    title:string,
    memberId: string,
    nickname: string,
    capability: number, 
    chatResponseDto: ChatResponse
}
  


interface ChatMessageResponse{
    chatRoomId:number,
    chatId:number,
    memberId:string,
    nickname:string,
    content:string,
    type:string,
}

interface UserChatRoomResponse{
    userChatRoomId:number,
    member:MemberSummaryResponse,
    lastAccessTime: Date,
}

interface ChatRoomDetail{
  chatRoomResponse: ChatRoomResponse,
  chatList: ChatMessageResponse[],
  userChatRoomList: UserChatRoomResponse[],
  chatRoomProduct: ChatRoomProduct,
}
interface ChatRoomProduct{
  title:string,
  productId:number,
}



  export type { ChatResponse, ChatRoomResponse,ChatMessageResponse,UserChatRoomResponse,ChatRoomDetail, ChatRoomProduct }