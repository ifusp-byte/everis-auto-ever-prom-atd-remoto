package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.CampoModeloNota;

@Repository
public interface CampoModeloNotaRepository extends JpaRepository<CampoModeloNota, Long> {
	
	@Query(value="SELECT D.NU_CONTEUDO_CAMPO_MULTIPLO, D.DE_CONTEUDO_CAMPO_MULTIPLO FROM AVL.AVLTB018_CAMPO_MODELO B,  AVL.AVLTB017_CAMPO_MODELO_NOTA C, AVL.AVLTB046_CNDO_CAMPO_MULTIPLO D WHERE B.NU_CAMPO_MODELO_NOTA = C.NU_CAMPO_MODELO_NOTA AND C.NU_CAMPO_MODELO_NOTA = D.NU_CAMPO_MODELO_NOTA AND B.NU_CAMPO_MODELO_NOTA =?1 GROUP BY D.NU_CONTEUDO_CAMPO_MULTIPLO, D.DE_CONTEUDO_CAMPO_MULTIPLO", nativeQuery=true)
	List<Object[]> modeloNotaDinamicoCampos(Long idCampoModeloNota);

}
