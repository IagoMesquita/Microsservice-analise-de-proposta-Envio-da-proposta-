package com.pieropan.propostaapp.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
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
  private final NotificationService notificationService;
  private final String exchangePendingProprosal;

  public ProposalService(
      ProposalRepository proposalRepository,
      NotificationService notificationService,
      @Value("${rabbitmq.pending-proposal.exchange}") String exchangePendingProprosal) {
    this.proposalRepository = proposalRepository;
    this.notificationService = notificationService;
    this.exchangePendingProprosal = exchangePendingProprosal;
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
      notificationService.notify(proposal, exchangePendingProprosal);

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
