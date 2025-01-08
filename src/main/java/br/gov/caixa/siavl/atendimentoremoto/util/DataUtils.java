package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.BR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_BANCO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_PADRAO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_PADRAO_SIIPC_FRONT_1;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_PADRAO_SIIPC_FRONT_2;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_SIECM;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_SIECM_ANEXO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_SIIPC;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.PT;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DATA_US;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		//time.add(Calendar.HOUR, -3);
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

	public String formataDataSiipc(Date dateInput) {
		String data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_SIIPC, locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

	public Boolean menorTrintaMinutos(Object dataInicial) {
		Long diferenca = calculaDiferencaDataMinutos(dataInicial); 
		return diferenca > 30 && diferenca < 120 ;
	}

	public Long diferencaDataMinutos(LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return Duration.between(dataInicial, dataFinal).toMinutes();
	}

	public Long calculaDiferencaDataMinutos(Object dataInicial) {
		LocalDateTime dataFinalFormat = formataDataCalcularDiferencaSiipc(String.valueOf(formataData(new Date())));
		LocalDateTime dataInicialFormat = formataDataCalcularDiferencaSiipc(
				String.valueOf(formataData(formataDataSiipc(dataInicial))));
		return diferencaDataMinutos(dataInicialFormat, dataFinalFormat);
	}

	public LocalDateTime formataDataCalcularDiferencaSiipc(Object object) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATA_PADRAO);
		return LocalDateTime.parse(String.valueOf(object), format);
	}

	public Date formataDataSiipc(Object object) {

		Date data = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfIn = new SimpleDateFormat(DATA_SIIPC, locale);
		SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_PADRAO, locale);

		try {
			data = sdfOut.parse(sdfOut.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String formataDataSiipcFront(Object object) {

		String diaMesAno = null;
		String horaMinuto = null;
		Locale locale = new Locale(PT, BR);
		SimpleDateFormat sdfIn = new SimpleDateFormat(DATA_SIIPC, locale);
		SimpleDateFormat sdfOutDiaMesAno = new SimpleDateFormat(DATA_PADRAO_SIIPC_FRONT_1, locale);
		SimpleDateFormat sdfOutHoraMinuto = new SimpleDateFormat(DATA_PADRAO_SIIPC_FRONT_2, locale);

		try {
			diaMesAno = String.valueOf(sdfOutDiaMesAno.format(sdfIn.parse(String.valueOf(object))));
			horaMinuto = String.valueOf(sdfOutHoraMinuto.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diaMesAno + " às " + horaMinuto;
	}

  public static String formataDataLocalTexto(String data) {
    
    Locale locale = new Locale(PT, BR);
    SimpleDateFormat sdfIn = new SimpleDateFormat(DATA_US, locale);
    SimpleDateFormat sdfOut = new SimpleDateFormat(DATA_PADRAO_SIIPC_FRONT_1, locale);

    try {
      data = sdfOut.format(sdfIn.parse(data));
    } catch (ParseException e) {
      e.printStackTrace();
    }
   return data;
  }
  
  public Date formataDataModelo(Object object) {

		Date data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.0'", locale);
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);

		try {
			data = sdfOut.parse(sdfOut.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String formataDataModelo(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
