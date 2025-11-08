package com.empresa.immediate.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.immediate.dao.ImmediateCreateDAO;
import com.empresa.immediate.dto.ImmediateCreateResponseDTO;
import com.empresa.immediate.dto.InfoAdicionalDTO;
import com.empresa.immediate.model.ImmediateModel;
import com.empresa.immediate.model.InfoAdicionalModel;
import com.empresa.immediate.repository.ImmediateRepository;

@Service
public class ImmediateCreateDAOImpl implements ImmediateCreateDAO {

	@Autowired
	ImmediateRepository immediateRepository;

	private static ModelMapper modelMapper = new ModelMapper();

	public void immediateCreateMvto(Map<String, String> immediateCreateHeaders,
			ImmediateCreateResponseDTO immediateCreateResponseDTO, UUID accessToken) {

		var immediateModel = ImmediateModel.builder().guid(UUID.randomUUID())
				.client_id(immediateCreateHeaders.get("apim_client_id").replace("\"", "")).cnpj(null)
				.acct_nr(immediateCreateHeaders.get("accountNumber").replace("\"", "")).qrc_String(null).dt_create(null)
				.dt_last_upd(null).guidapi(immediateCreateHeaders.get("apim_guid").replace("\"", "")).codStatus(1)
				.dt_expiration(null).acc_token(accessToken.toString())
				.vlr(immediateCreateResponseDTO.getValor().getOriginal()).location(null)
				.txid(immediateCreateResponseDTO.getTxid()).dev_cpf(null).dev_cnpj(null)
				.dev_name(immediateCreateResponseDTO.getDevedor().getNome())
				.mod_alt(immediateCreateResponseDTO.getValor().getModalidadeAlteracao())
				.sol_pag(immediateCreateResponseDTO.getSolicitacaoPagador()).revisao(1)
				.addInfo(new HashSet<>(addInfoBuild(immediateCreateResponseDTO))).build();

		immediateRepository.save(immediateModel);
	}

	public List<InfoAdicionalModel> addInfoBuild(ImmediateCreateResponseDTO immediateCreateResponseDTO) {

		List<InfoAdicionalDTO> infoAdicionalDTOList = immediateCreateResponseDTO.getInfoAdicionais();

		return infoAdicionalDTOList.stream().map(this::convertToModel).collect(Collectors.toList());

	}

	private InfoAdicionalModel convertToModel(InfoAdicionalDTO infoAdicionalDTO) {

		return modelMapper.map(infoAdicionalDTO, InfoAdicionalModel.class);
	}

}
