package com.pieropan.propostaapp.dto;

import lombok.Getter;
import lombok.Setter;

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

  public ProposalRequestDto() {
  }

  public ProposalRequestDto(String name, String lastName, String phoneNumber, String cpf,
      Double income, Double requestedAmount, int paymentTerm) {
    this.name = name;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.cpf = cpf;
    this.income = income;
    this.requestedAmount = requestedAmount;
    this.paymentTerm = paymentTerm;
  }

}
