package com.curso.reservaveiculosapi.controller;

import com.curso.reservaveiculosapi.model.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {




    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public MessageResponse chat(@DestinationVariable Long roomId, MessageResponse message){
        System.out.println("Chegou");
        System.out.println(message);
        return new MessageResponse(message.message(), message.userId(), message.userName());
    }

}
