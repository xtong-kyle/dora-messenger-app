package com.dora.app.service;

import com.dora.app.common.constant.DoraMessengerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoraMessengerProducerService {

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String message) {
        template.send(DoraMessengerConstants.TOPIC_, message);
    }


}
