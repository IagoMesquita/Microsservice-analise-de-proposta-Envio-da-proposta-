package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.ProposalRequestDto;
import com.pieropan.propostaapp.dto.ProposalResponseDto;
import com.pieropan.propostaapp.entity.Proposal;
import com.pieropan.propostaapp.mapper.ProposalMapper;
import com.pieropan.propostaapp.repository.ProposalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {

  private final ProposalRepository proposalRepository;
  private final NotificationRabbitMQService notificationRabbitMQService;
  private final String exchangePendingProposal;

  public ProposalService(
      ProposalRepository proposalRepository,
      NotificationRabbitMQService notificationRabbitMQService,
      @Value("${rabbitmq.pending-proposal.exchange}") String exchangePendingProposal
     ) {
    this.proposalRepository = proposalRepository;
    this.notificationRabbitMQService = notificationRabbitMQService;
    this.exchangePendingProposal = exchangePendingProposal;
  }


  //  @Autowired
//  public ProposalService(ProposalRepository proposalRepository) {
//    this.proposalRepository = proposalRepository;
//  }
  public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
    Proposal proposalDb = ProposalMapper.INSTANCE.convertDtoToProposal(proposalRequestDto);
    proposalRepository.save(proposalDb);


    notifyRabbitMQ(proposalDb);

    return ProposalMapper.INSTANCE.convertProposalToDto(proposalDb);
  }

  // Metodo pensado para Resiliencia no servico com RabbitMQ. Caso a conexao falhe a propriedade Integration que foi pensada para isso, sera alterada e posteriormente esses registros serao trabalhados apra irem para as filas
  private void notifyRabbitMQ(Proposal proposal) {
    try {
      notificationRabbitMQService.notify(proposal, exchangePendingProposal);

    } catch (RuntimeException exception) {
      proposal.setIntegrada(false);
      proposalRepository.save(proposal);
    }
  }


  public List<ProposalResponseDto> getAll() {
    return ProposalMapper.INSTANCE.convertListEntityToListDto(
        proposalRepository.findAll()
    );
  }
}
