package br.gov.caixa.siavl.atendimentoremoto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.gov.caixa.siavl.atendimentoremoto.dto.AtendimentoClienteOutputDto;

@Mapper
public interface MinhasNotasMapper {

	MinhasNotasMapper INSTANCE = Mappers.getMapper(MinhasNotasMapper.class);

	AtendimentoClienteOutputDto toAtendimentoClienteOutputDto(Object[] atendimentoCliente);

}
