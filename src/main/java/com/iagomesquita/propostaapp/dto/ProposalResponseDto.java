package com.iagomesquita.propostaapp.dto;

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
  private String nome;
  private String sobrenome;
  private String telefone;
  private String cpf;
  private Double renda;

  private String valorSolicitadoFmt;
  private int prazoPagamento;
  private Boolean aprovada;
  private String observacao;


}
