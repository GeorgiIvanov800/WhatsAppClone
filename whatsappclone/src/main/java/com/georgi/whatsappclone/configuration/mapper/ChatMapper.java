package com.georgi.whatsappclone.configuration.mapper;

import com.georgi.whatsappclone.model.ChatResponse;
import com.georgi.whatsappclone.model.entity.ChatEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatResponse toChatResponse(ChatEntity chat, String senderId) {



        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessage(senderId))
                .lastMessage(chat.getLastMessage())
                .isReceiverOnline(chat.getReceiver().isUserOnline())
                .senderId(chat.getSender().getId())
                .receiverId(chat.getReceiver().getId())
                .lastMessageTime(chat.getLastMessageTime())
                .build();
    }
}
