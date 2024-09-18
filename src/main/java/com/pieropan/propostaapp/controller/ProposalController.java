package com.pieropan.propostaapp.controller;

import com.pieropan.propostaapp.dto.ProposalRequestDto;
import com.pieropan.propostaapp.dto.ProposalResponseDto;
import com.pieropan.propostaapp.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/proposal")
public class ProposalController {

  private final ProposalService proposalService;

//  @Autowired
//  public ProposalController(ProposalService proposalService) {
//      this.proposalService = proposalService;
//  }

  @PostMapping
  public ResponseEntity<ProposalResponseDto> create(@RequestBody ProposalRequestDto proposalRequestDto) {
    ProposalResponseDto response = proposalService.create(proposalRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
