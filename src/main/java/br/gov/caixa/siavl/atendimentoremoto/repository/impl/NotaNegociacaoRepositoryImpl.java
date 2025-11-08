package br.gov.caixa.siavl.atendimentoremoto.repository.impl;

import java.util.ArrayList;
import java.util.List;

import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SuppressWarnings("all")
public class NotaNegociacaoRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	public List<ContrataNotaInputDTO> dadosNota(Long numeroNota) {

		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT ");
		sb.append(" D.NU_NOTA_NEGOCIACAO, ");
		sb.append(" A.NU_CPF_CLIENTE, ");
		sb.append(" A.NU_CNPJ_CLIENTE, ");
		sb.append(" D.VR_SOLICITADO_NOTA_NEGOCIACAO, ");
		sb.append(" D.NU_SITUACAO_NOTA, ");
		sb.append(" E.DE_SITUACAO_NOTA, ");
		sb.append(" CASE D.IC_SISTEMA_CADASTRO_NOTA ");
		sb.append(" WHEN 'P' THEN 'PNC' ");
		sb.append(" WHEN 'A' THEN 'LEGADO' ");
		sb.append(" WHEN 'I' THEN 'AUTOMATIZADA' ");
		sb.append(" END AS ORIGEM_SISTEMA_CADASTRO_NOTA ");
		sb.append(" FROM AVL.AVLTB001_ATENDIMENTO_CLIENTE A, ");
		sb.append(" AVL.AVLTB003_ATENDIMENTO_NEGOCIO B, ");
		sb.append(" AVL.AVLTB002_NEGOCIO_AGNCA_VIRTUAL C, ");
		sb.append(" AVL.AVLTB019_NOTA_NEGOCIACAO D, ");
		sb.append(" AVL.AVLTB007_SITUACAO_NOTA_NGCAO E, ");
		sb.append(" AVL.AVLTB005_MODELO_NOTA_NEGOCIO F, ");
		sb.append(" AVL.AVLTB020_ACAO_PRODUTO G, ");
		sb.append(" AVL.AVLTB081_CANAL_ATENDIMENTO H ");
		sb.append(" WHERE A.NU_PROTOCOLO_ATNTO_CLNTE = B.NU_PROTOCOLO_ATNTO_CLNTE ");
		sb.append(" AND B.NU_NEGOCIO_AGENCIA_VIRTUAL = C.NU_NEGOCIO_AGENCIA_VIRTUAL ");
		sb.append(" AND C.NU_NEGOCIO_AGENCIA_VIRTUAL = D.NU_NEGOCIO_AGENCIA_VIRTUAL ");
		sb.append(" AND D.NU_SITUACAO_NOTA = E.NU_SITUACAO_NOTA ");
		sb.append(" AND D.NU_MODELO_NOTA_NEGOCIO = F.NU_MODELO_NOTA_NEGOCIO ");
		sb.append(" AND F.NU_ACAO_PRODUTO = G.NU_ACAO_PRODUTO ");
		sb.append(" AND A.NU_CANAL_ATENDIMENTO = H.NU_CANAL_ATENDIMENTO ");
		sb.append(" AND D.NU_NOTA_NEGOCIACAO = :numeroNota ");

		List<Object[]> dadosNota = em.createNativeQuery(sb.toString()).setParameter("numeroNota", numeroNota)
				.getResultList();

		List<ContrataNotaInputDTO> dadosNotaLista = new ArrayList<>();

		if (!dadosNota.isEmpty()) {
			dadosNota.stream().forEach(nota -> {
				ContrataNotaInputDTO notaDados = new ContrataNotaInputDTO();
				notaDados.setNumeroNota(nota[0] != null ? String.valueOf(nota[0]) : null);
				notaDados.setCpf(nota[1] != null ? String.valueOf(nota[1]) : null);
				notaDados.setCnpj(nota[2] != null ? String.valueOf(nota[2]) : null);
				notaDados.setValor(nota[3] != null ? Double.parseDouble(String.valueOf(nota[3])) : null);
				notaDados.setNumeroSituacaoNota(nota[4] != null ? String.valueOf(nota[4]) : null);
				notaDados.setDescricaoSituacaoNota(nota[5] != null ? String.valueOf(nota[5]) : null);
				notaDados.setOrigemNota(nota[6] != null ? String.valueOf(nota[6]) : null);
				dadosNotaLista.add(notaDados);
			});
		}

		return dadosNotaLista;
	}

}
