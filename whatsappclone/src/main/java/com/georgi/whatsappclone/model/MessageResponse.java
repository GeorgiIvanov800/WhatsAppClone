package com.georgi.whatsappclone.model;

import com.georgi.whatsappclone.model.enums.MessageState;
import com.georgi.whatsappclone.model.enums.MessageType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private String content;
    private MessageType type;
    private MessageState state;
    private String senderId;
    private String receiverId;
    private LocalDateTime createdAt;
    private byte[] media;
}
