package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.BR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_BANCO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_PADRAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_SIECM;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_SIECM_ANEXO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PT;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("all")
public class DataUtils {

	public String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_PADRAO, locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	public Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

	public Date formataDataValidade(int prazoValidade, String horaValidade) {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		time.add(Calendar.DATE, prazoValidade);
		time.add(Calendar.HOUR, Integer.parseInt(horaValidade.substring(0, 1)));
		time.add(Calendar.MINUTE, Integer.parseInt(horaValidade.substring(3, 4)));

		return time.getTime();
	}

	public Date formataData(Object object) {

		Date data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfIn = new SimpleDateFormat(DATA_BANCO, locale);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_PADRAO, locale);

		try {
			data = sdfOut.parse(sdfOut.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String formataDataSiecmAnexo(Date dateInput) {

		String data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_SIECM_ANEXO, locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	public String formataDataSiecm(Date dateInput) {

		String data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_SIECM, locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
