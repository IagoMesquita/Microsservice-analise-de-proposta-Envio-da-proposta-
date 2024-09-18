package com.pieropan.propostaapp.entity;

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

  private Double requestedAmount;

  private int paymentTerm;

  private Boolean isApproved;

  private boolean integrated;

  private String observation;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  private User user;


}
