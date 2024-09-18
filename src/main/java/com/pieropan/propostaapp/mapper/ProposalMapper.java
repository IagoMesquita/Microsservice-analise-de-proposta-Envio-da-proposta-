package com.pieropan.propostaapp.mapper;

import com.pieropan.propostaapp.dto.ProposalRequestDto;
import com.pieropan.propostaapp.dto.ProposalResponseDto;
import com.pieropan.propostaapp.entity.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProposalMapper {

  ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

  @Mapping( source = "name", target = "user.name")
  @Mapping( source = "lastName", target = "user.lastName")
  @Mapping( source = "cpf", target = "user.cpf")
  @Mapping( source = "phoneNumber", target = "user.phoneNumber")
  @Mapping( source = "income", target = "user.income")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "isApproved", ignore = true)
  @Mapping(target = "integrated", ignore = true)
  @Mapping(target = "observation", ignore = true)
  Proposal convertDtoToProposal(ProposalRequestDto proposalRequestDto);

  @Mapping(source = "user.name", target = "name")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "user.cpf", target = "cpf")
  @Mapping(source = "user.phoneNumber", target = "phoneNumber")
  @Mapping(source = "user.income", target = "income")
  ProposalResponseDto convertProposalToDto(Proposal proposal);


}
