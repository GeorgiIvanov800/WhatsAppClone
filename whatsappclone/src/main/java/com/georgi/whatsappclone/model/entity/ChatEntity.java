package com.georgi.whatsappclone.model.entity;

import com.georgi.whatsappclone.model.enums.MessageState;
import com.georgi.whatsappclone.model.enums.MessageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class ChatEntity extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC ")
    private List<MessageEntity> messages;


    @Transient
    public String getChatName(final String senderId) {
        if (receiver.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return receiver.getFirstName() + " " + receiver.getLastName();
    }

    @Transient
    public long getUnreadMessage(final String senderId) {
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();

    }

    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if(messages.get(0).getType() != MessageType.TEXT) {
                return "Attachment";
            } else {
                messages.get(0).getContent();
            }
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate();
        }
        return null;
    }

}
