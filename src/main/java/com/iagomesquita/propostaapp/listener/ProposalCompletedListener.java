package com.iagomesquita.propostaapp.listener;

import com.iagomesquita.propostaapp.entity.Proposal;
import com.iagomesquita.propostaapp.repository.ProposalRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProposalCompletedListener {

  private final ProposalRepository proposalRepository;

  public ProposalCompletedListener(ProposalRepository proposalRepository) {
    this.proposalRepository = proposalRepository;
  }

  @RabbitListener(queues = "${rabbitmq.completed-proposal.queue}")
  public void proposalCompletedListener(Proposal proposal) {
    proposalRepository.save(proposal);
  }
}
