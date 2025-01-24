package com.georgi.whatsappclone.configuration.mapper;

import com.georgi.whatsappclone.model.MessageResponse;
import com.georgi.whatsappclone.model.entity.MessageEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {


    public MessageResponse toMessageResponse(MessageEntity message) {

        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                //TODO read the media file
                .build();
    }
}
