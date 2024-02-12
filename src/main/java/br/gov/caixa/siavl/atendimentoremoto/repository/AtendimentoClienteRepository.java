package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;

@Repository
public interface AtendimentoClienteRepository extends JpaRepository<AtendimentoCliente, Long> {

	@Query("SELECT " + "A.cpfCliente, " + "A.cnpjCliente, " + "A.nomeCliente, " + "G.descricao, "
			+ "D.dataCriacaoNota, " + "D.numeroNota, " + "E.descricao " + "FROM " + "AtendimentoCliente A, "
			+ "AtendimentoNegocio B, " + "NegocioAgenciaVirtual C, " + "NotaNegociacao D, "
			+ "SituacaoNotaNegociacao E, " + "ModeloNotaNegocio F, " + "AcaoProduto G " + "WHERE "
			+ "A.numeroProtocolo = B.numeroProtocolo " + "AND " + "B.numeroNegocio = C.numeroNegocio " + "AND "
			+ "C.numeroNegocio = D.numeroNegocio " + "AND " + "D.numeroSituacaoNota = E.numeroSituacaoNota " + "AND "
			+ "D.numeroModeloNota = F.numeroModeloNota " + "AND " + "F.numeroAcao = G.numeroAcao " + "AND "
			+ "E.numeroSituacaoNota IN (16, 17, 21, 22, 23, 24)" + "AND " + "D.numeroMatriculaCriacaoNota = ?1")
	List<Object[]> findByMatriculaAtendente(Long numeroMatriculaCriacaoNota);

}
