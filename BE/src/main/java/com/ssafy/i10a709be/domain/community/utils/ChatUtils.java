package com.ssafy.i10a709be.domain.community.utils;

import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatUtils {
    public static List<UserChatRoom> convertMemberToUserChatRoom(List<Member> joinMembers, ChatRoom chatRoom, LocalDateTime createDate) {
        List<UserChatRoom> userChatRooms = new ArrayList<>();
        for (Member member : joinMembers) {
            UserChatRoom userChatRoom = UserChatRoom.builder()
                    .member(member)
                    .lastAccessTime(createDate)
                    .build();
            userChatRoom.updateChatRoom(chatRoom);
            userChatRooms.add(userChatRoom);
        }
        return userChatRooms;
    }
}
