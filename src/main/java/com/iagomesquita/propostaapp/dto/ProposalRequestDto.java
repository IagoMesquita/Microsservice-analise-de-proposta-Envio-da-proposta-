package com.iagomesquita.propostaapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProposalRequestDto {

  private String nome;
  private String sobrenome;
  private  String telefone;
  private String cpf;
  private Double renda;
  private Double valorSolicitado;
  private int prazoPagamento;


}
