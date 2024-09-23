package com.iagomesquita.propostaapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private String sobrenome;
  private String cpf;
  private String telefone;
  private Double renda;

  @OneToOne(mappedBy = "usuario")
//  @JsonBackReference *
  private Proposal proposta;

}


// * Descomentar Caso d^e erro ciclico por conta da serializacao do metodo babbitTemplata em RabbitMQConfig