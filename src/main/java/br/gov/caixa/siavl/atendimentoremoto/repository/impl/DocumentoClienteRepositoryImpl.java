package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;

import br.gov.caixa.siavl.atendimentoremoto.dto.DocumentoByNotaDTO;
import br.gov.caixa.siavl.atendimentoremoto.enums.TipoDocumentoAnexoEnum;

@SuppressWarnings("all")
public class DocumentoClienteRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<DocumentoByNotaDTO> documentosByNota(Long numeroNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT TB19.NU_NOTA_NEGOCIACAO, ");
		sb.append(" TB34.NU_TIPO_DOCUMENTO_CLIENTE, ");
		sb.append(" TB39.NO_TIPO_DOCUMENTO_CLIENTE, ");
		sb.append(" TB33.CO_GED_ANEXO, ");
		sb.append(" TB33.NO_ANEXO ");
		sb.append(" FROM  AVL.AVLTB033_DOCUMENTO_CLIENTE TB33 ");
		sb.append(" INNER JOIN AVL.AVLTB034_DCMNTO_NOTA_NGCCO TB34 ");
		sb.append(" ON (TB34.NU_CPF_CNPJ_CLIENTE = TB33.NU_CPF_CNPJ_CLIENTE ");
		sb.append(" AND TB34.IC_TIPO_PESSOA = TB33.IC_TIPO_PESSOA ");
		sb.append(" AND TB34.TS_INCLUSAO_DOCUMENTO = TB33.TS_INCLUSAO_DOCUMENTO ");
		sb.append(" AND TB34.NU_TIPO_DOCUMENTO_CLIENTE = ");
		sb.append(" TB33.NU_TIPO_DOCUMENTO_CLIENTE) ");
		sb.append(" INNER JOIN AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE TB39 ");
		sb.append(" ON TB34.NU_TIPO_DOCUMENTO_CLIENTE = ");
		sb.append(" TB39.NU_TIPO_DOCUMENTO_CLIENTE ");
		sb.append(" INNER JOIN AVL.AVLTB019_NOTA_NEGOCIACAO TB19 ");
		sb.append(" ON TB34.NU_NOTA_NEGOCIACAO = TB19.NU_NOTA_NEGOCIACAO ");
		sb.append(" WHERE TB19.IC_SISTEMA_CADASTRO_NOTA = 'P' ");
		sb.append(" AND TB19.NU_NOTA_NEGOCIACAO = :numeroNota ");
		sb.append(" ORDER BY TB33.TS_INCLUSAO_DOCUMENTO DESC  ");

		List<Object[]> documentosByNota = em.createNativeQuery(sb.toString()).setParameter("numeroNota", numeroNota)
				.getResultList();

		List<DocumentoByNotaDTO> documentos = new ArrayList<>();

		if (!documentosByNota.isEmpty()) {
			documentosByNota.stream().forEach(documento -> {
				DocumentoByNotaDTO documentoByNota = new DocumentoByNotaDTO();
				documentoByNota.setNumeroNota(String.valueOf(documento[0]));
				documentoByNota.setNumeroTipoDocumento(String.valueOf(documento[1]));
				documentoByNota.setNomeTipoDocumento(String.valueOf(documento[2]));
				documentoByNota.setCodGed(String.valueOf(documento[3]));
				documentoByNota.setNomeAnexo(String.valueOf(documento[4]));

				if (String.valueOf(documento[4]).contains(TipoDocumentoAnexoEnum.ACEITE.getDescricao())) {
					documentoByNota.setCategoriaDocumento(TipoDocumentoAnexoEnum.ACEITE.getDescricao());
				}

				if (String.valueOf(documento[4]).contains(TipoDocumentoAnexoEnum.OPCIONAL.getDescricao())) {
					documentoByNota.setCategoriaDocumento(TipoDocumentoAnexoEnum.OPCIONAL.getDescricao());
				}

				if (String.valueOf(documento[4]).contains(TipoDocumentoAnexoEnum.OBRIGATORIO.getDescricao())) {
					documentoByNota.setCategoriaDocumento(TipoDocumentoAnexoEnum.OBRIGATORIO.getDescricao());
				}

				documentoByNota.setNomeAnexoFormatado(formataNomeDocumento(documentoByNota.getNomeAnexo(),
						String.valueOf(numeroNota), documentoByNota.getCategoriaDocumento()));

				documentos.add(documentoByNota);
			});
		}

		return documentos;
	}

	private static String formataNomeDocumento(String nomeAnexo, String numeroNota, String categoria) {
		String nomeAnexoFormatado = StringUtils.EMPTY;
		if (nomeAnexo != null) {
			nomeAnexoFormatado = nomeAnexo.replaceAll("_" + numeroNota + "-DOCUMENTO_" + categoria + "_",
					" ");
		}
		return nomeAnexoFormatado;
	}
}
