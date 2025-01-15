package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

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
public class ClasseDocumentoAtributos {

	private String classeDocumento;
	private String nomeCampo;
	private String tipoCampo;
	private Boolean campoObrigatorio;
	private Integer tamanhoMaximo;
	private String valor;
	private Boolean habilitado;
	private TipoMascara tipoMascara;
	private String[] selectValues;

}
