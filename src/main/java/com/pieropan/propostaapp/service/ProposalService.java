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
    Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(proposalRequestDto);
    proposalRepository.save(proposal);

    ProposalResponseDto responseDto = ProposalMapper.INSTANCE.convertProposalToDto(proposal);
    notificationService.notify(responseDto, exchangePendingProprosal);

    return responseDto;
  }

  public List<ProposalResponseDto> getAll() {
    return ProposalMapper.INSTANCE.convertListEntityToListDto(
        proposalRepository.findAll()
    );
  }
}
