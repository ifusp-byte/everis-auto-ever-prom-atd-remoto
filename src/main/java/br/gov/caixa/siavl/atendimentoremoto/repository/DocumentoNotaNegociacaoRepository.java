package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoNotaNegociacao;

@Repository
public interface DocumentoNotaNegociacaoRepository extends JpaRepository<DocumentoNotaNegociacao, Long> {

	@Query(value = "DELETE FROM DocumentoNotaNegociacao A WHERE A.tipoPessoa = ?1 AND A.numeroNota = ?2 AND A.cpfCnpjCliente = ?3 AND A.tipoDocumentoCliente = ?4 AND A.inclusaoDocumento = ?5")
	void excluiDocumento(String tipoPessoa, Long numeroNota, Long cpfCnpjCliente, Long tipoDocumentoCliente,
			Date inclusaoDocumento);

}
