package br.gov.caixa.siavl.atendimentoremoto.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CamposDinamicosModeloNotaValidacaoDTO")
public class CamposDinamicosModeloNotaValidacaoDTO {

	private String idCampoDinamico;
	private String nomeCampoDinamico;
	private String flagbrigatorio;
	private String flagTipoDado;
	private String quantidadeCaracteres;
	private String mascaraCampo;

	private String valorInput;

	public CamposDinamicosModeloNotaValidacaoDTO(Long idCampoDinamico, String nomeCampoDinamico, Long flagbrigatorio,
			String flagTipoDado, Long quantidadeCaracteres) {
		this.idCampoDinamico = String.valueOf(idCampoDinamico);
		this.nomeCampoDinamico = nomeCampoDinamico;
		this.flagbrigatorio = String.valueOf(flagbrigatorio);
		this.flagTipoDado = flagTipoDado;
		this.quantidadeCaracteres = String.valueOf(quantidadeCaracteres);
	}
	
	
	public CamposDinamicosModeloNotaValidacaoDTO(Long idCampoDinamico, String nomeCampoDinamico, Long flagbrigatorio,
			String flagTipoDado, Long quantidadeCaracteres, String mascaraCampo) {
		this.idCampoDinamico = String.valueOf(idCampoDinamico);
		this.nomeCampoDinamico = nomeCampoDinamico;
		this.flagbrigatorio = String.valueOf(flagbrigatorio);
		this.flagTipoDado = flagTipoDado;
		this.quantidadeCaracteres = String.valueOf(quantidadeCaracteres);
		this.mascaraCampo = mascaraCampo != null ? String.valueOf(mascaraCampo) : null;
	}

}
