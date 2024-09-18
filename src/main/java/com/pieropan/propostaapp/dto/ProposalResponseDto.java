package com.pieropan.propostaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProposalResponseDto {

  private Long id;
  private String name;
  private String lastName;
  private String phoneNumber;
  private String cpf;
  private Double income;
  private Double requestedAmount;
  private int paymentTerm;

  private Boolean isApproved;

  private String observation;


}
