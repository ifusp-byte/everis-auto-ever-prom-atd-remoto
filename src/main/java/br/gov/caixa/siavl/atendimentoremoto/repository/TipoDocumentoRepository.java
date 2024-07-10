package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoCliente, Long> {
	
	static final String GED_PJ = "'CONTRATACAO_GIRO_CAIXA', 'CONTRATACAO_CARTAO_VA_VR', 'CONTRATO_CHEQUE_EMPRESA', 'CONTRATO_DESCONTO_CHEQ_PRE_DAT', 'CONTRATO_FAMPE', 'CONTRATO_GIRO_CAIXA_INST_MULTIP', 'CONTRATO_PRONAMPE', 'CONTRATO_RENEGOCIACAO_PJ'";
	static final String GED_PF = "'CONTRATO_CHEQUE_ESPECIAL', 'CONTRATO_CONSIGNADO', 'CONTRATO_CREDITO_PESSOAL', 'CONTRATO_ADESAO_HOMEBROKER', 'CONTRATO_CDC', 'CONTRATO_FINANCIAMENTO_VEICULO'";

	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED IN ("+ GED_PF +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> tipoDocumentoPF();
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED IN ("+ GED_PJ +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> tipoDocumentoPJ();
	
	@Query(value="SELECT A.NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED LIKE ?1", nativeQuery = true)
	Long numeroTipoDocumentoCliente(String codGED);
	
	@Query(value="SELECT A.CO_CLASSE_DOCUMENTO_GED FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NU_TIPO_DOCUMENTO_CLIENTE = ?1", nativeQuery = true)
	String categoriaDocumentoCliente(Long numeroTipoDocumento);
}
