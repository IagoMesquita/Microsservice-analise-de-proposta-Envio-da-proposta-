package com.iagomesquita.propostaapp.repository;

import com.iagomesquita.propostaapp.entity.Proposal;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProposalRepository extends CrudRepository<Proposal, Long> {
  List<Proposal> findAllByIntegradaIsFalse();

  // Forma alternativa a usar diretamente o metodo save, que altera todos os dados
  @Transactional
  @Modifying
  @Query(value = "UPDATE proposals SET aprovada = :isApproved, observacao = :observation, WHERE id = :id", nativeQuery = true)
  void updateProposal(Long id, boolean isApproved, String observation);

}
