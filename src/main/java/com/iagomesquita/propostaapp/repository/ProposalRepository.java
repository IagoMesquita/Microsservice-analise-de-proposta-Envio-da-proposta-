package com.iagomesquita.propostaapp.repository;

import com.iagomesquita.propostaapp.entity.Proposal;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ProposalRepository extends CrudRepository<Proposal, Long> {
  List<Proposal> findAllByIntegradaIsFalse();

}
