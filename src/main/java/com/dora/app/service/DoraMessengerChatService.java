package com.dora.app.service;

import com.dora.app.common.enums.MessageType;
import com.dora.app.entity.model.ChatUserModel;
import com.dora.app.entity.model.MessageModel;
import com.dora.app.entity.request.DoraMessengerConversationRequest;
import com.dora.app.entity.request.MessageChatRequest;
import com.dora.app.entity.response.DoraMessengerConversationResponse;
import com.dora.app.repository.ConversationRepository;
import com.dora.app.repository.MessageRepository;
import com.dora.app.repository.UserDetailRepository;
import com.dora.app.repository.entity.ConversationEntity;
import com.dora.app.repository.entity.MessageEntity;
import com.dora.app.repository.entity.UserEntity;
import com.dora.app.repository.enums.ConversationTableEnum;
import com.dora.app.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DoraMessengerChatService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    private MessageRepository messageRepository;

    public void sendMessageToUser(MessageChatRequest chatRequest) throws Exception {
        String messageId = messageRepository.saveMessage(chatRequest.getConversationId(), chatRequest.getSenderId(), chatRequest.getMessageModel().getType().name(), chatRequest.getMessageModel().getText());
        chatRequest.getMessageModel().setId(messageId);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatRequest.getReceiverId()),
                "/queue/messages",
                chatRequest.getMessageModel());
    }

    public DoraMessengerConversationResponse createConversation(DoraMessengerConversationRequest conversationRequest) throws Exception {
        if (null == conversationRequest.getParticipants() || conversationRequest.getParticipants().isEmpty() || conversationRequest.getParticipants().size() <= 1) {
            return null;
        }
        List<UserEntity> userInfoList = userDetailRepository.getMultipleUserBaseInfoByIds(conversationRequest.getParticipants());
        if (userInfoList.isEmpty() || userInfoList.size() != conversationRequest.getParticipants().size()) {
            return null;
        }
        ConversationTableEnum type = conversationRequest.getParticipants().size() > 2 ? ConversationTableEnum.GROUP : ConversationTableEnum.STANDALONE;
        String conversationId = conversationRepository.saveConversation(conversationRequest.getName(), type, conversationRequest.getParticipants());
        conversationRequest.setConversationId(conversationId);
        return buildUserResponseChats(conversationId, conversationRequest.getName(), null, userInfoList);
    }

    public DoraMessengerConversationResponse getConversation(DoraMessengerConversationRequest conversationRequest) throws Exception {
        if (null == conversationRequest.getParticipants() || conversationRequest.getParticipants().isEmpty() || conversationRequest.getParticipants().size() <= 1) {
            return null;
        }
        List<UserEntity> userInfoList = userDetailRepository.getMultipleUserBaseInfoByIds(conversationRequest.getParticipants());
        if (userInfoList.isEmpty() || userInfoList.size() != conversationRequest.getParticipants().size()) {
            return null;
        }
        Optional<ConversationEntity> optional = conversationRepository.getConversationById(conversationRequest.getConversationId());
        return optional.map(conversationEntity -> buildUserResponseChats(conversationEntity.getId(), conversationEntity.getName(), conversationEntity.getTimestamp(), userInfoList)).orElse(null);
    }




    private DoraMessengerConversationResponse buildUserResponseChats(String id, String name, Timestamp createTime, List<UserEntity> users) {
        DoraMessengerConversationResponse conversationResponse = new DoraMessengerConversationResponse();
        conversationResponse.setConversationId(id);
        conversationResponse.setName(name);
        List<ChatUserModel> result = new ArrayList<>();
        List<MessageEntity> messages = null;
        if (createTime != null) {
            messages = messageRepository.getConversationMessages(id, createTime.getTime());
        }
        for (UserEntity user : users) {
            ChatUserModel chatUserModel = new ChatUserModel();
            chatUserModel.setUserId(user.getId());
            chatUserModel.setUsername(user.getUsername());
            if (messages != null) {
                MessageEntity latestMessage = messages.get(messages.size() - 1);
                List<MessageModel> messageModels = messages.stream()
                        .filter(msg -> user.getId() != null && Objects.equals(msg.getUserId(), user.getId()))
                        .map(msg ->
                                new MessageModel(msg.getId(), msg.getUserId(), MessageType.valueOf(msg.getType()), msg.getText(), msg.getCreateTime()))
                        .toList();
                Optional<Map.Entry<ChronoUnit, Long>> chronoUnitLongEntry = DateTimeUtil.findLargestUnitDiff(latestMessage.getCreateTime().getTime(), System.currentTimeMillis());
                chatUserModel.setLastActivity(convertChrono(chronoUnitLongEntry.get().getKey(), chronoUnitLongEntry.get().getValue()));
                chatUserModel.setLatestMessage(latestMessage.getText());
                chatUserModel.setLastestMessageType(MessageType.valueOf(latestMessage.getType()));
            }
            result.add(chatUserModel);
        }
        List<MessageModel> messageModels = messages.stream().map(msg ->
                new MessageModel(msg.getId(), msg.getUserId(), MessageType.valueOf(msg.getType()), msg.getText(), msg.getCreateTime())).toList();
        conversationResponse.setMessageModels(messageModels);
        conversationResponse.setParticipants(result);
        return conversationResponse;
    }

    private String convertChrono(ChronoUnit unit, Long time) {
        return String.valueOf(time) + unit.name().toLowerCase().charAt(0);
    }

}
