package com.pieropan.propostaapp.service;

import com.pieropan.propostaapp.dto.ProposalRequestDto;
import com.pieropan.propostaapp.dto.ProposalResponseDto;
import com.pieropan.propostaapp.entity.Proposal;
import com.pieropan.propostaapp.mapper.ProposalMapper;
import com.pieropan.propostaapp.repository.ProposalRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProposalService {

  private ProposalRepository proposalRepository;

//  @Autowired
//  public ProposalService(ProposalRepository proposalRepository) {
//    this.proposalRepository = proposalRepository;
//  }
  public ProposalResponseDto create(ProposalRequestDto proposalRequestDto) {
    Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(proposalRequestDto);
    Proposal proposalSaved = proposalRepository.save(proposal);

    return ProposalMapper.INSTANCE.convertProposalToDto(proposalSaved);
  }
}
