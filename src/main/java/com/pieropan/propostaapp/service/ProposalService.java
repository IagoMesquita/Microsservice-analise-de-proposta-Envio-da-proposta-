package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.ProposalRequestDto;
import com.pieropan.propostaapp.dto.ProposalResponseDto;
import com.pieropan.propostaapp.entity.Proposal;
import com.pieropan.propostaapp.mapper.ProposalMapper;
import com.pieropan.propostaapp.repository.ProposalRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProposalService {

  private ProposalRepository proposalRepository;
  private NotificationService notificationService;



//  @Autowired
//  public ProposalService(ProposalRepository proposalRepository) {
//    this.proposalRepository = proposalRepository;
//  }
  public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
    Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(proposalRequestDto);
    proposalRepository.save(proposal);

    ProposalResponseDto responseDto = ProposalMapper.INSTANCE.convertProposalToDto(proposal);
    notificationService.notify(responseDto, "proposta-pendente.ex");

    return responseDto;
  }

  public List<ProposalResponseDto> getAll() {
    return ProposalMapper.INSTANCE.convertListEntityToListDto(
        proposalRepository.findAll()
    );
  }
}
