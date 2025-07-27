package com.dora.app.api;

import com.dora.app.entity.DoraMessengerBaseResponse;
import com.dora.app.entity.response.MessengerUserInfoResponse;
import com.dora.app.service.DoraMessengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dora.app.common.constant.DoraMessengerConstants.*;


@RestController
@RequestMapping("/user")
public class DoraMessengerUserAPI {

    @Autowired
    private DoraMessengerUserService messengerUserService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        try {
            MessengerUserInfoResponse response = messengerUserService.getUserChatInfoByName(username);
            if (response != null) {
                return ResponseEntity.ok(
                        new DoraMessengerBaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK, RESP_MESSAGE_SUCCESS,response));
            }
            return ResponseEntity.ok(
                    new DoraMessengerBaseResponse<>(HttpStatus.OK.value(), HttpStatus.OK, RESP_MESSAGE_NOT_FOUND, response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new DoraMessengerBaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, RESP_MESSAGE_INTERNAL_SERVER_ERROR));
        }
    }

}
