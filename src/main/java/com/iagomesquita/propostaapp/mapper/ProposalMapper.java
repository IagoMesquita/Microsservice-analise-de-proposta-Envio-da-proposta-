package com.iagomesquita.propostaapp.mapper;

import com.iagomesquita.propostaapp.dto.ProposalRequestDto;
import com.iagomesquita.propostaapp.dto.ProposalResponseDto;
import com.iagomesquita.propostaapp.entity.Proposal;
import java.text.NumberFormat;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProposalMapper {

  ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

  @Mapping( source = "nome", target = "usuario.nome")
  @Mapping( source = "sobrenome", target = "usuario.sobrenome")
  @Mapping( source = "cpf", target = "usuario.cpf")
  @Mapping( source = "telefone", target = "usuario.telefone")
  @Mapping( source = "renda", target = "usuario.renda")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "aprovada", ignore = true)
  @Mapping(target = "integrada", constant = "true")
  @Mapping(target = "observacao", ignore = true)
  Proposal convertDtoToProposal(ProposalRequestDto proposalRequestDto);

  @Mapping(source = "usuario.nome", target = "nome")
  @Mapping(source = "usuario.sobrenome", target = "sobrenome")
  @Mapping(source = "usuario.cpf", target = "cpf")
  @Mapping(source = "usuario.telefone", target = "telefone")
  @Mapping(source = "usuario.renda", target = "renda")
  @Mapping(expression = "java(setValorSolicitadoFmt(proposal))", target = "valorSolicitadoFmt")
  ProposalResponseDto convertProposalToDto(Proposal proposal);

  //Poderia utilizar um map com convertProposalToDto no service ao inves dessa conversao mais essa conversao
  List<ProposalResponseDto> convertListEntityToListDto(Iterable<Proposal> proposals);

  default String setValorSolicitadoFmt(Proposal proposal) {
    return NumberFormat.getCurrencyInstance().format(proposal.getValorSolicitado());
  }
}
