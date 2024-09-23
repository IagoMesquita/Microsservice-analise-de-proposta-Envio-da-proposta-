package com.iagomesquita.propostaapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "proposals")
public class Proposal {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double valorSolicitado;

  private int prazoPagamento;

  private Boolean aprovada;

  private boolean integrada;

  private String observacao;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
//  @JsonManagedReference *
  private User usuario;


}


// * Descomentar Caso d^e erro ciclico por conta da serializacao do metodo babbitTemplata em RabbitMQConfig