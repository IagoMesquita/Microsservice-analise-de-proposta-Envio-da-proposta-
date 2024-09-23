package com.iagomesquita.propostaapp.service;

import com.iagomesquita.propostaapp.entity.Proposal;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationRabbitMQService {

  private  RabbitTemplate rabbitTemplate;

  public void notify(Proposal proposal, String exchange) {
    rabbitTemplate.convertAndSend(exchange, "", proposal);
  }


}
