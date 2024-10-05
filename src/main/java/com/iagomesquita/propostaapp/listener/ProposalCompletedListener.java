package com.iagomesquita.propostaapp.listener;

import com.iagomesquita.propostaapp.dto.ProposalResponseDto;
import com.iagomesquita.propostaapp.entity.Proposal;
import com.iagomesquita.propostaapp.mapper.ProposalMapper;
import com.iagomesquita.propostaapp.repository.ProposalRepository;
import com.iagomesquita.propostaapp.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProposalCompletedListener {

  private final ProposalRepository proposalRepository;

  private final WebSocketService webSocketService;

  public ProposalCompletedListener(ProposalRepository proposalRepository,
      WebSocketService webSocketService) {
    this.proposalRepository = proposalRepository;
    this.webSocketService = webSocketService;
  }

  @RabbitListener(queues = "${rabbitmq.completed-proposal.queue}")
  public void proposalCompletedListener(Proposal proposal) {
    proposalRepository.save(proposal);
    ProposalResponseDto proposalResponseDto = ProposalMapper.INSTANCE.convertProposalToDto(
        proposal);
    webSocketService.notify(proposalResponseDto);
  }

  private void updateProposal(Proposal proposal) {
    proposalRepository.updateProposal(
        proposal.getId(), proposal.getAprovada(), proposal.getObservacao()
    );
  }
}
