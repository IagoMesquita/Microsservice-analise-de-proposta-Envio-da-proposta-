package com.pieropan.propostaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProposalRequestDto {

  private String name;
  private String lastName;
  private  String phoneNumber;
  private String cpf;
  private Double income;
  private Double requestedAmount;
  private int paymentTerm;


}
