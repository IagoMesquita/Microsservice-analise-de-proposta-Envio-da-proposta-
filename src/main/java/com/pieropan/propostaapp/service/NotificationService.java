package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.ProposalResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

  private  RabbitTemplate rabbitTemplate;

  public void notify(ProposalResponseDto proposal, String exchange) {
    rabbitTemplate.convertAndSend(exchange, "", proposal);
  }


}
