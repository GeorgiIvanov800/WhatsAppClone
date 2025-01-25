package com.georgi.whatsappclone.service;

import com.georgi.whatsappclone.configuration.mapper.MessageMapper;
import com.georgi.whatsappclone.model.MessageRequest;
import com.georgi.whatsappclone.model.MessageResponse;
import com.georgi.whatsappclone.model.Notification;
import com.georgi.whatsappclone.model.entity.ChatEntity;
import com.georgi.whatsappclone.model.entity.MessageEntity;
import com.georgi.whatsappclone.model.enums.MessageState;
import com.georgi.whatsappclone.model.enums.MessageType;
import com.georgi.whatsappclone.model.enums.NotificationType;
import com.georgi.whatsappclone.repository.ChatRepository;
import com.georgi.whatsappclone.repository.MessageRepository;
import com.georgi.whatsappclone.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public void save(MessageRequest messageRequest) {

        ChatEntity chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        MessageEntity message = new MessageEntity();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(chat.getSender().getId());
        message.setReceiverId(chat.getReceiver().getId());
        message.setType(messageRequest.getType());
        message.setState(MessageState.SEEN);

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .messageType(messageRequest.getType())
                .content(messageRequest.getContent())
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .type(NotificationType.MESSAGE)
                .chatName(chat.getChatName(message.getSenderId()))
                .build();

        notificationService.sendNotification(message.getReceiverId(), notification);
    }

    public List<MessageResponse> findChatMessages(String chatId) {

        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {

        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String receiverId = getReceiverId(chat, authentication);

        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.SEEN)
                .receiverId(receiverId)
                .receiverId(getSenderId(chat, authentication))
                .build();

        notificationService.sendNotification(receiverId, notification);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        ChatEntity chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String receiverId = getReceiverId(chat, authentication);
        final String filePath = fileService.saveFile(file, senderId);

        MessageEntity message = new MessageEntity();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SEEN);
        message.setMediaFilePath(filePath);
        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.IMAGE)
                .messageType(MessageType.IMAGE)
                .senderId(senderId)
                .receiverId(receiverId)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();

        notificationService.sendNotification(receiverId, notification);
    }

    private String getSenderId(ChatEntity chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getReceiver().getId();
    }

    private String getReceiverId(ChatEntity chat, Authentication authentication) {

        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getReceiver().getId();
        }

        return chat.getSender().getId();
    }
}
