package com.georgi.whatsappclone.model;

import com.georgi.whatsappclone.model.enums.MessageType;
import com.georgi.whatsappclone.model.enums.NotificationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String content;
    private String senderId;
    private String receiverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;
}
