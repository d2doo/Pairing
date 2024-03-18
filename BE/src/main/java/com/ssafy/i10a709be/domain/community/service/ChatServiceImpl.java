package com.ssafy.i10a709be.domain.community.service;

import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomDeleteDto;
import com.ssafy.i10a709be.domain.community.entity.ChatRoom;
import com.ssafy.i10a709be.domain.community.entity.UserChatRoom;
import com.ssafy.i10a709be.domain.community.repository.ChatRoomRepository;
import com.ssafy.i10a709be.domain.community.repository.UserChatRoomRepository;
import com.ssafy.i10a709be.domain.community.utils.ChatUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    @Override
    public List<ChatRoom> findJoinedChatRooms(String memberId) {
        List<ChatRoom> joinedChatRooms = chatRoomRepository.findByMemberId(memberId);
        return joinedChatRooms;
    }
    public Optional<ChatRoom> findById(Long chatRoomId ){
        return chatRoomRepository.findById( chatRoomId );
    }

    @Transactional
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
                .build();
        List<UserChatRoom> userChatRooms = ChatUtils.convertMemberToUserChatRoom(chatRoomCreateDto.getJoinMembers(), chatRoom, chatRoomCreateDto.getCreateDate());
        chatRoomRepository.save(chatRoom);

        return chatRoom.getChatRoomId();
    }

    @Override
    public void deleteChatRoom(ChatRoomDeleteDto chatRoomDeleteDto) {

    }

    @Override
    public Optional<ChatRoom> findChatRoomByChatRoomIdAndChats(Long chatRoomId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findChatRoomByChatRoomIdAndChats( chatRoomId );
        return Optional.of( findChatRoom ).orElseThrow( () -> new InternalServerException("해당하는 채팅방이 없습니다.", this));
    }


}
