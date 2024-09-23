package com.iagomesquita.propostaapp.controller;

import com.iagomesquita.propostaapp.dto.ProposalRequestDto;
import com.iagomesquita.propostaapp.dto.ProposalResponseDto;
import com.iagomesquita.propostaapp.service.ProposalService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class ProposalController {

  private final ProposalService proposalService;

//  @Autowired
//  public ProposalController(ProposalService proposalService) {
//      this.proposalService = proposalService;
//  }

  @PostMapping
  public ResponseEntity<ProposalResponseDto> create(
      @RequestBody ProposalRequestDto proposalRequestDto) {
    ProposalResponseDto response = proposalService.create(proposalRequestDto);
    return ResponseEntity.created(
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri()
    ).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ProposalResponseDto>> getAllProposal() {
    return ResponseEntity.ok().body(proposalService.getAll());
  }


}

//Codigo para adcionar Location no retorno do Header. Boa pratica da Rest:

/*
 ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri()
* */
