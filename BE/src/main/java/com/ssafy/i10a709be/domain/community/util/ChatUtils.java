package com.ssafy.i10a709be.domain.community.util;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.List;

public class ChatUtils {
    public static void addUserChatRoomsOnChatRoom(List<Member> joinMembers, ChatRoom chatRoom) {
        for (Member member : joinMembers) {
            chatRoom.getUserChatRooms().add(
                UserChatRoom.builder()
                        .member(member)
                        .lastAccessTime(chatRoom.getCreatedAt())
                        .chatRoom( chatRoom )
                        .build()
            );
        }
    }
}
