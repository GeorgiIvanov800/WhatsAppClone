package com.georgi.whatsappclone.repository;

import com.georgi.whatsappclone.model.constant.MessageConstants;
import com.georgi.whatsappclone.model.entity.ChatEntity;
import com.georgi.whatsappclone.model.entity.MessageEntity;
import com.georgi.whatsappclone.model.enums.MessageState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<MessageEntity> findMessagesByChatId(String chatId);

    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
