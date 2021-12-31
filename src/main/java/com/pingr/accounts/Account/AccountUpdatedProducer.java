package com.pingr.accounts.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountUpdatedProducer {
    @Value(value = "${topic.accountUpdated}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessage(Account message) {
        //System.out.println("ENVIANDO MENSAGEM DE UPDATED");
        this.template.send(this.topic, message);
    }
}
