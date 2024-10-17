package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.caixa.siavl.atendimentoremoto.model.TipoDocumentoCliente;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoCliente, Long> {
	
	static final String OBRIGATORIO_GED_PJ = "'CONTRATACAO_GIRO_CAIXA', 'CONTRATACAO_CARTAO_VA_VR', 'CONTRATO_CHEQUE_EMPRESA', 'CONTRATO_DESCONTO_CHEQ_PRE_DAT', 'CONTRATO_FAMPE', 'CONTRATO_GIRO_CAIXA_INST_MULTIP', 'CONTRATO_PRONAMPE', 'CONTRATO_RENEGOCIACAO_PJ'";
	static final String OBRIGATORIO_GED_PF = "'CONTRATO_CHEQUE_ESPECIAL', 'CONTRATO_CONSIGNADO', 'CONTRATO_CREDITO_PESSOAL', 'CONTRATO_ADESAO_HOMEBROKER', 'CONTRATO_CDC', 'CONTRATO_FINANCIAMENTO_VEICULO'";

	static final String OPCIONAL_GED_PF = "'Boleto Engenharia', 'DAMP', 'Documento de Margem', 'Dossiê Habitacional', 'Nota de Negociação LCI', 'Termo de Adesão INSS'";
	static final String OPCIONAL_GED_PJ = "'Consulta CPEND', 'Consulta CADIN', 'Consulta CND Estadual', 'Consulta CND Federal', 'Consulta CNDT', 'Consulta CNPJ/QSA', 'Consulta CONRES', 'Consulta CRF', 'Consulta SICOW', 'Consulta SIJUR', 'Consulta TJDFT'";
	static final String OPCIONAL_GED_AMBOS_PF_PJ = "'Análise de Perfil Investidor', 'Cancelamento Seguro Prestamista', 'Conversa WhatsApp',  'Nota de Negociação CDB',  'Pedido de Oferta Pública',  'PA de Conformidade',  'Proposta Seguro Prestamista',  'Requisição/Consulta jurídica',  'Simulação',  'Telas de erro Sistemas',  'Outros'";
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED IN ("+ OBRIGATORIO_GED_PF +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> obrigatorioTipoDocumentoPF();
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED IN ("+ OBRIGATORIO_GED_PJ +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> obrigatorioTipoDocumentoPJ();
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NO_TIPO_DOCUMENTO_CLIENTE IN ("+ OPCIONAL_GED_PF +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> opcionalTipoDocumentoPF();
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NO_TIPO_DOCUMENTO_CLIENTE IN ("+ OPCIONAL_GED_PJ +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> opcionalTipoDocumentoPJ();
	
	@Query(value = "SELECT * FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NO_TIPO_DOCUMENTO_CLIENTE IN ("+ OPCIONAL_GED_AMBOS_PF_PJ +") ORDER BY A.NO_TIPO_DOCUMENTO_CLIENTE ASC", nativeQuery = true)
	List<TipoDocumentoCliente> opcionalTipoDocumentoAmbosPFPJ();
	
	@Query(value="SELECT A.NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.CO_CLASSE_DOCUMENTO_GED LIKE ?1", nativeQuery = true)
	Long numeroTipoDocumentoCliente(String codGED);
	
	@Query(value="SELECT A.NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NO_TIPO_DOCUMENTO_CLIENTE LIKE ?1", nativeQuery = true)
	Long numeroTipoDocumentoByNomeDocumento(String nomeTipoDocumento);
	
	@Query(value="SELECT A.CO_CLASSE_DOCUMENTO_GED FROM AVL.AVLTB039_TIPO_DOCUMENTO_CLNTE A WHERE A.NU_TIPO_DOCUMENTO_CLIENTE = ?1", nativeQuery = true)
	String categoriaDocumentoCliente(Long numeroTipoDocumento);
}
