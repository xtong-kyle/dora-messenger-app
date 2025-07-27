package com.dora.app.api;

import com.dora.app.entity.DoraMessengerBaseResponse;
import com.dora.app.entity.request.DoraMessengerConversationRequest;
import com.dora.app.entity.request.MessageChatRequest;
import com.dora.app.entity.response.DoraMessengerConversationResponse;
import com.dora.app.service.DoraMessengerChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import static com.dora.app.common.constant.DoraMessengerConstants.*;

@RestController
@RequestMapping("/chat")
public class DoraMessengerChatAPI {

    @Autowired
    private DoraMessengerChatService messengerChatService;

    @MessageMapping("/sendMsg")
    public void sendMessageToUser(@Payload MessageChatRequest chatRequest) {
        try {
            messengerChatService.sendMessageToUser(chatRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/getUsersChatlog")
    public ResponseEntity<?> getUsersChatlog(@RequestBody DoraMessengerConversationRequest request) {
        DoraMessengerConversationResponse response = null;
        try {
            if (request.getConversationId() != null) {
                response = messengerChatService.getConversation(request);
            } else {
                response = messengerChatService.createConversation(request);
            }
            if (response != null) {
                return ResponseEntity.ok(
                        new DoraMessengerBaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK, RESP_MESSAGE_SUCCESS, response));
            }
            return ResponseEntity.ok(
                    new DoraMessengerBaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK, RESP_MESSAGE_FAIL, response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new DoraMessengerBaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, RESP_MESSAGE_INTERNAL_SERVER_ERROR));
        }
    }

}
