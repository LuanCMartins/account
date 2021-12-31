package com.pingr.accounts.Account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

//CONSUMER FEITO UNICAMENTE PARA TESTE SE ESTAVA SENDO ENVIADO CORRETAMENTE O EVENTO DE DESFAZER AMIZADE
@Component
public class FriendshipUnmadeConsumer {
    private final AccountService service;

    @Autowired
    public FriendshipUnmadeConsumer(AccountService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${topic.friendship.desfeita}",
            groupId = "account_friendship_unmade"
    )
    public void consume(String message) throws IOException  {
        System.out.println("consumiu friendship unmade");
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        List<Account> accounts = mapper.readValue(message, new TypeReference<>() {
        });
        for (Account acc : accounts) {
            System.out.println(acc.toString());
        }
    }
}
