package br.gov.caixa.siavl.atendimentoremoto.gateway.simtr.dto;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.BR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_PADRAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SimtrDocumentoDto")
public class SimtrDocumentoDto {

	private String id;
	private String tipologia;
	private String acordeonMfe;
	private String nome;
	private String ativo;
	private String situacaoDocumento;
	private String dataHoraCaptura;
	private String mimeType;
	
	
	public Date getDataCaptura() {
		
		Date data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdf = new SimpleDateFormat(DATA_PADRAO, locale);

		try {
			data = sdf.parse(dataHoraCaptura);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return data;
		
	}

}
