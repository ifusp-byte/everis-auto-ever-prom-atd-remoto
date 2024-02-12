package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocioFavorito;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class ModeloNotaServiceImpl implements ModeloNotaService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	public List<ModeloNotaOutputDto> consultaModeloNota() {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();

		modeloNotaRepository.findModeloNota().stream().forEach(modeloNota ->

		{
			ModeloNotaOutputDto modeloNotaOutputDto = null;

			modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNota[0]))
					.numeroAcaoProduto(String.valueOf(modeloNota[1]))
					.descricaoAcaoProduto(String.valueOf(modeloNota[2])).build();
			modelosNota.add(modeloNotaOutputDto);
		});
		return modelosNota;
	}
	
	public List<ModeloNotaOutputDto> consultaModeloNotaMaisUtilizada() {
		List<ModeloNotaOutputDto> modelosNota = new ArrayList<>();

		modeloNotaRepository.findModeloNotaMaisUtilizada().stream().forEach(modeloNota ->

		{
			ModeloNotaOutputDto modeloNotaOutputDto = null;

			modeloNotaOutputDto = ModeloNotaOutputDto.builder()
					.numeroModeloNota(String.valueOf(modeloNota[1]))
					.numeroAcaoProduto(String.valueOf(modeloNota[2]))
					.descricaoAcaoProduto(String.valueOf(modeloNota[3]))
					.build();
			modelosNota.add(modeloNotaOutputDto);
		});
		return modelosNota.subList(0, 5);
	}

	@Override
	public List<ModeloNotaOutputDto> consultaModeloNotaFavorita(String token) {

		Long matriculaAtendente = null;
		matriculaAtendente = matriculaCriacaoNota(token);

		List<ModeloNotaOutputDto> modelosNotaFavorita = new ArrayList<>();
		List<ModeloNotaOutputDto> modelosNotaFavorita2 = new ArrayList<>();

		modeloNotaRepository.findModeloNotaFavorita(matriculaAtendente).stream().forEach(modeloNotaFavorita ->

		{
			ModeloNotaOutputDto modeloNotaOutputDto = null;

			modeloNotaOutputDto = ModeloNotaOutputDto.builder().numeroModeloNota(String.valueOf(modeloNotaFavorita[0]))
					.numeroAcaoProduto(String.valueOf(modeloNotaFavorita[1]))
					.descricaoAcaoProduto(String.valueOf(modeloNotaFavorita[2]))
					.dataEscolhaFavorito(formataData(modeloNotaFavorita[3])).build();

			modelosNotaFavorita.add(modeloNotaOutputDto);
		});

		modelosNotaFavorita2.addAll(modelosNotaFavorita);

		for (ModeloNotaOutputDto favorita : modelosNotaFavorita) {
			for (int i = 0; i < modelosNotaFavorita.size(); i++) {
				if (modelosNotaFavorita.get(i).getNumeroModeloNota().equals(favorita.getNumeroModeloNota())
						&& modelosNotaFavorita.get(i).getNumeroAcaoProduto().equals(favorita.getNumeroAcaoProduto())
						&& modelosNotaFavorita.get(i).getDataEscolhaFavorito()
								.after(favorita.getDataEscolhaFavorito())) {
					modelosNotaFavorita2.remove(favorita);
				}
			}
		}
		return modelosNotaFavorita2.subList(0, 5);
	}

	@Override
	public Boolean adicionaModeloNotaFavorita(String token, Long numeroModeloNota) {

		Boolean statusNotaFavorita = null;
		Long matriculaCriacaoNota = null;
		matriculaCriacaoNota = matriculaCriacaoNota(token);

		ModeloNotaNegocioFavorito modeloNotaNegocioFavorito = new ModeloNotaNegocioFavorito();
		modeloNotaNegocioFavorito.setMatriculaFavorito(matriculaCriacaoNota);
		modeloNotaNegocioFavorito.setNumeroModeloNota(numeroModeloNota);
		modeloNotaNegocioFavorito.setDataEscolhaFavorito(new Date());

		modeloNotaRepository.save(modeloNotaNegocioFavorito);
		statusNotaFavorita = true;
		return statusNotaFavorita;
	}

	public Long matriculaCriacaoNota(String token) {
		return Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));
	}

	private Date formataData(Object object) {

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

}
