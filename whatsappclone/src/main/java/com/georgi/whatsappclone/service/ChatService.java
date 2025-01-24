package com.georgi.whatsappclone.service;

import com.georgi.whatsappclone.configuration.mapper.ChatMapper;
import com.georgi.whatsappclone.model.ChatResponse;
import com.georgi.whatsappclone.model.entity.ChatEntity;
import com.georgi.whatsappclone.model.entity.UserEntity;
import com.georgi.whatsappclone.repository.ChatRepository;
import com.georgi.whatsappclone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper mapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatByReceiverId(Authentication currentUser) {

        final String userId = currentUser.getName();

        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map( c -> mapper.toChatResponse(c, userId))
                .toList();
    }

    public String createChat(String senderId, String receiverId) {
        Optional<ChatEntity> existingChat = chatRepository.findChatByReceiverAndSender(senderId, receiverId);

        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        UserEntity sender = userRepository.findByPublicId(senderId)
                .orElseThrow( () -> new EntityNotFoundException("User with ID " + senderId + " not found"));

        UserEntity receiver = userRepository.findByPublicId(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + receiverId + " not found") );

        ChatEntity chat = new ChatEntity();

        chat.setSender(sender);
        chat.setReceiver(receiver);

        ChatEntity savedChat = chatRepository.save(chat);
        return savedChat.getId();
    }
}
