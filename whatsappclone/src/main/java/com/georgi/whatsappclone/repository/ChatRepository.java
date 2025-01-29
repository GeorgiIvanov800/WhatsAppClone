package com.georgi.whatsappclone.repository;

import com.georgi.whatsappclone.model.ChatResponse;
import com.georgi.whatsappclone.model.constant.ChatConstants;
import com.georgi.whatsappclone.model.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, String> {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    List<ChatEntity> findChatsBySenderId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    Optional<ChatEntity> findChatByReceiverAndSender(String senderId, String receiverId);
}
