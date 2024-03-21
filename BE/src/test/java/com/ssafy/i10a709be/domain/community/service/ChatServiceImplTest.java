package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatServiceImplTest {

    @Autowired
    private ChatServiceImpl chatService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    void createChatRoom() {
        Member member1 = Member.builder()
                .email("cqqudgjs1@naver.com")
                .nickname("라이빵허")
                .provider(OAuthProvider.KAKAO)
                .build();
        memberRepository.save( member1 );

        Member member2 = Member.builder()
                .email("cqqudgjs2@naver.com")
                .nickname("라이빵허")
                .provider(OAuthProvider.KAKAO)
                .build();
        memberRepository.save( member2 );

        Member member3 = Member.builder()
                .email("cqqudgjs3@naver.com")
                .nickname("라이빵허")
                .provider(OAuthProvider.KAKAO)
                .build();
        memberRepository.save( member3 );
        List<Member> joinMembers = new ArrayList<>();
        joinMembers.add( member1 );
        joinMembers.add( member2 );
        joinMembers.add( member3 );

        Long value = chatService.createChatRoom( new ChatRoomCreateDto( joinMembers, member1.getMemberId(), "temp", 10, ChatRoomStatus.active, LocalDateTime.now() ) );
        assertNotNull( value );

        ChatRoom chatRoom = chatService.findById( value );
        assertNotNull( chatRoom );

        List<UserChatRoom> userChatRooms = chatRoom.getUserChatRooms();
        assertEquals( userChatRooms.size(), 3 );
        assertEquals( userChatRooms.get(0).getChatRoom().getChatRoomId(), chatRoom.getChatRoomId() );
    }
}