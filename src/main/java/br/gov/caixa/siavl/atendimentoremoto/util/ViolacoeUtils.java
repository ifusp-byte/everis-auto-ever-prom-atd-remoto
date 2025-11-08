package br.gov.caixa.siavl.atendimentoremoto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;

@Component
@SuppressWarnings("all")
public class ViolacoeUtils {

	public static ViolacaoDto violacaoNova(String mensagem, String valor) {
		ViolacaoDto violacaoDto = new ViolacaoDto();
		violacaoDto.setPropriedade(mensagem);
		violacaoDto.setValorInformado(valor);
		return violacaoDto;
	}

	public static List<ViolacaoDto> violacaoNovas(String mensagem, String valor, List<ViolacaoDto> violacoes) {
		ViolacaoDto violacaoDto = new ViolacaoDto();
		violacaoDto.setPropriedade(mensagem);
		violacaoDto.setValorInformado(valor);
		violacoes.add(violacaoDto);
		return violacoes;
	}

	public static List<ViolacaoDto> violacaoLista(List<List<ViolacaoDto>> lista) {
		return !lista.isEmpty() ? lista.get(0) : new ArrayList<>();
	}

	public static List<ViolacaoDto> violacaoAgrupada(List<ViolacaoDto> violacoes) {

		Map<String, ViolacaoDto> mapa = new HashMap<String, ViolacaoDto>();

		for (ViolacaoDto violacao : violacoes) {

			String chave = violacao.getPropriedade() + violacao.getValorInformado();

			if (!mapa.containsKey(chave)) {
				mapa.put(chave, violacao);

			}

		}

		return new ArrayList<ViolacaoDto>(mapa.values());
	}
}
