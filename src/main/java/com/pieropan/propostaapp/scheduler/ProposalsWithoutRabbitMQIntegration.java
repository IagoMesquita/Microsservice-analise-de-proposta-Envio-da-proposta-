package com.pieropan.propostaapp.scheduler;

import com.pieropan.propostaapp.repository.ProposalRepository;
import com.pieropan.propostaapp.service.NotificationRabbitMQService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProposalsWithoutRabbitMQIntegration {

  private final ProposalRepository proposalRepository;
  private  final NotificationRabbitMQService notificationRabbitMQService;
  private final String exchangePendingProposal;

  private final Logger logger = LoggerFactory.getLogger(ProposalsWithoutRabbitMQIntegration.class);



  public ProposalsWithoutRabbitMQIntegration(
      ProposalRepository proposalRepository,
      NotificationRabbitMQService notificationRabbitMQService,
      @Value("${rabbitmq.pending-proposal.exchange}") String exchangePendingProposal) {
    this.proposalRepository = proposalRepository;
    this.notificationRabbitMQService = notificationRabbitMQService;
    this.exchangePendingProposal = exchangePendingProposal;
  }

  @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
  public void searchProposalsWithoutIntegrationRabbitMQ() {
    proposalRepository.findAllByIntegradaIsFalse().forEach(proposal -> {
      try {
        notificationRabbitMQService.notify(proposal, exchangePendingProposal);
        proposal.setIntegrada(true);
        proposalRepository.save(proposal);
      }catch (RuntimeException exception) {
        logger.error(exception.getMessage());
      }
    });
  }

}
