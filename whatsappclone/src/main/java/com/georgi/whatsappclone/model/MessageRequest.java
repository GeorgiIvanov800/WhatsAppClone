package com.georgi.whatsappclone.model;

import com.georgi.whatsappclone.model.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {

    private String content;

    private String senderId;

    private String receiverId;

    private MessageType type;

    private String chatId;
}
