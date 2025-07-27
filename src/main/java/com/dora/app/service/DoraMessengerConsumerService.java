package com.dora.app.service;

import com.dora.app.common.constant.DoraMessengerConstants;
import com.dora.app.entity.request.MessageChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoraMessengerConsumerService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = DoraMessengerConstants.TOPIC_, groupId = "dora_chat")
    public void receiveMessage(MessageChatRequest chatRequest) {
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatRequest.getSenderId()),
                "/queue/messages",
                String.valueOf(chatRequest.getReceiverId()) + ": " + chatRequest.getConversationId());
    }
}
