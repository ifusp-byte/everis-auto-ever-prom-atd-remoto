package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.ModeloNotaNegocio;

@Repository
public interface ModeloNotaRepository extends JpaRepository<ModeloNotaNegocio, Long> {

	@Query("SELECT N FROM ModeloNotaNegocio N WHERE ROWNUM <= 1 AND N.numeroModeloNota = ?1")
	ModeloNotaNegocio prazoValidade(Long numeroModeloNota);

	@Query("SELECT N FROM ModeloNotaNegocio N WHERE N.vinculaDocumento = 0 AND N.numeroModeloNota = ?1")
	Optional<ModeloNotaNegocio> vinculaDocumento(Long numeroModeloNota);

}
