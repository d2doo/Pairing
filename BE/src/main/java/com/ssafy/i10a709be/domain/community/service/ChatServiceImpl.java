package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.domain.community.dto.ChatMessageRequestDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDeleteDto;
import com.ssafy.i10a709be.domain.community.entity.Chat;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.community.repository.ChatRepository;
import com.ssafy.i10a709be.domain.community.repository.ChatRoomRepository;
import com.ssafy.i10a709be.domain.community.repository.UserChatRoomRepository;
import com.ssafy.i10a709be.domain.community.utils.ChatUtils;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;

    //채팅방 조회 서비스 with 채팅 내역
    @Override
    public List<ChatRoom> findJoinedChatRooms(String memberId) {
       List<ChatRoom> joinedChatRooms = chatRoomRepository.findChatRoomsByJoinedMemberId(memberId);
       return joinedChatRooms;
    }
    public ChatRoom findChatRoomById(Long chatRoomId ){
        return chatRoomRepository.findById( chatRoomId ).orElseThrow( () -> new IllegalArgumentException(chatRoomId + "번 채팅방 조회간 에러 발생") );
    }

    @Override
    public Long createChatRoom(ChatRoomCreateDto chatRoomCreateDto) {
        ChatRoom chatRoom = ChatRoom.builder()
                .member(chatRoomCreateDto.getJoinMembers()
                        .stream()
                        .filter(m -> m.getMemberId().equals(chatRoomCreateDto.getMemberId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("채팅방에 참여하는 인원 중, 방장을 찾을 수 없습니다.")))
                .title(chatRoomCreateDto.getTitle())
                .capability(chatRoomCreateDto.getCapability())
                .status(chatRoomCreateDto.getStatus())
                .chats( new ArrayList<>() )
                .userChatRooms( new ArrayList<>() )
                .build();
        chatRoomRepository.save(chatRoom);
        ChatUtils.addUserChatRoomsOnChatRoom(chatRoomCreateDto.getJoinMembers(), chatRoom);

        return chatRoom.getChatRoomId();
    }



    @Override
    public void deleteChatRoom(ChatRoomDeleteDto chatRoomDeleteDto) {

    }

    @Override
    public Chat saveChatMessage(ChatMessageRequestDto chatMessageRequestDto, Long roomId) {
        Chat chat = Chat.builder()
                .member(memberRepository.findById(chatMessageRequestDto.getMemberId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")))
                .chatRoom(chatRoomRepository.findById(roomId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 채팅방을 찾을 수 없습니다.")))
                .content(chatMessageRequestDto.getContent())
                .type(chatMessageRequestDto.getType())
                .fileId(chatMessageRequestDto.getFileId())
                .build();
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public List<Chat> findChatsByChatRoomId(Long roomId) {
        List<Chat> chats = chatRepository.findChatsByChatRoom_ChatRoomId( roomId );
        return chats;
    }

    @Override
    public List<UserChatRoom> findUserChatRoomsByChatRoomId(Long roomId) {
        return userChatRoomRepository.findUserChatRoomsByChatRoomId(roomId);
    }
}
