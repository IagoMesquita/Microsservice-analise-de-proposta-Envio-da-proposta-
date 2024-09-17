package com.pieropan.propostaapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "proposals")
public class Proposal {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private Double requestedAmount;

private String paymentTerm;

private Boolean isApproved;

private boolean integrated;

private String observation;

@OneToOne
@JoinColumn(name = "user_id")
private User user;


  public Proposal() {
  }

  public Proposal(Double requestedAmount, String paymentTerm, Boolean isApproved, boolean integrated,
      String observation) {
    this.requestedAmount = requestedAmount;
    this.paymentTerm = paymentTerm;
    this.isApproved = isApproved;
    this.integrated = integrated;
    this.observation = observation;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getRequestedAmount() {
    return requestedAmount;
  }

  public void setRequestedAmount(Double requestedAmount) {
    this.requestedAmount = requestedAmount;
  }

  public String getPaymentTerm() {
    return paymentTerm;
  }

  public void setPaymentTerm(String paymentTerm) {
    this.paymentTerm = paymentTerm;
  }

  public Boolean getApproved() {
    return isApproved;
  }

  public void setApproved(Boolean approved) {
    isApproved = approved;
  }

  public boolean getIntegrated() {
    return integrated;
  }

  public void setIntegrated(boolean integrated) {
    this.integrated = integrated;
  }

  public String getObservation() {
    return observation;
  }

  public void setObservation(String observation) {
    this.observation = observation;
  }
}
