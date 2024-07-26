package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.documentos;

import java.io.Serializable;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ClasseDocumento implements Serializable {

	CNH(ClasseDocumentoCNH.values()), 
	CARTEIRA_IDENTIDADE(ClasseDocumentoRG.values()),
	DEMONSTRATIVO_PAGAMENTO(ClasseDocumentoDemonstrativoPagamento.values()),
	DOCUMENTO_CONCESSIONARIA(ClasseDocumentoConcessionaria.values()),
	EXTRATO_CONTA(ClasseDocumentoExtratoBancario.values()), 
	CTPS(ClasseDocumentoCTPS.values()),
	DECLARACAO_IRPF(ClasseDocumentoIR.values()), 
	EXTRATO_FGTS(ClasseDocumentoFGTS.values()),
	EXTRATO_APLICACAO(ClasseDocumentoExtratoAplicacao.values()),
	FATURA_CARTAO_CREDITO(ClasseDocumentoFaturaCartaoCredito.values()),
	BOLETO_BANCARIO_HABITACIONAL(ClasseDocumentoBoletBancarioHabitacional.values()),
	CARTEIRA_FUNCIONAL(ClasseDocumentoCarteiraFuncional.values()),
	CARTEIRA_IDENTIDADE_MILITAR(ClasseDocumentoCarteiraIdentidadeMilitar.values()),
	PASSAPORTE(ClasseDocumentoPassaporte.values()), 
	CONTRATO_CDC(ClasseDocumentoContratoCDC.values()),
	CONTRATO_CONSIGNADO(ClasseDocumentoContratoConsignado.values()),
	CONTRATO_CREDITO_PESSOAL(ClasseDocumentoContratoCreditoPessoal.values()),
	CONTRATO_FINANCIAMENTO_VEICULO(ClasseDocumentoContratoFinanciamentoVeiculo.values()),
	CONTRATO_CHEQUE_ESPECIAL(ClasseDocumentoContratoChequeEspecial.values()),
	CONTRATO_ADESAO_HOMEBROKER(ClasseDocumentoContratoAdesaoHomebroker.values()),
	NOTA_NEGOCIACAO_LCI(ClasseDocumentoNegociacaoLCI.values()),
	NOTA_NEGOCIACAO_CDB(ClasseDocumentoNegociacaoCDB.values()),
	PEDIDO_OFERTA_PUBLICA(ClasseDocumentoPedidoOfertaPublica.values()),
	TERMO_ADESAO_INSS(ClasseTermoAdesaoInss.values()),
	PROPOSTA_SEGURO_PRESTAMISTA(ClassePropostaSeguroPrestamista.values()),
	CANCELAMENTO_SEGURO_PRESTAMISTA(ClasseCancelamentoSeguroPrestamista.values()),
	ANALISE_PERFIL_INVESTIDOR(ClasseAnalisePerfilInvestidor.values()),
	CONTRATACAO_CARTAO_VA_VR(ClasseContratacaoCartaoVaVr.values()),
	CONTRATACAO_GIRO_CAIXA(ClasseContratacaoGiroCaixa.values()),
	CONTRATO_CHEQUE_EMPRESA(ClasseContratoChequeEmpresa.values()), 
	CONTRATO_FAMPE(ClasseContratoFampe.values()),
	CONTRATO_DESCONTO_CHEQ_PRE_DAT(ClasseContratoDescontoChequePreDatado.values()),
	CONTRATO_GIRO_CAIXA_INST_MULTIP(ClasseGiroCaixaInstantaneoMultiplo.values()),
	CONTRATO_RENEGOCIACAO_PJ(ClasseContratoRenegociacaoPj.values()), 
	CONTRATO_PRONAMPE(ClasseContratoPronampe.values()),
	DOCUMENTO_CONSTITUICAO_PJ(ClasseDocumentoContituicaoPJ.values()), 
	CONTRATO_SOCIAL(ClasseContratoSocial.values()),
	DOCUMENTO_FATURAMENTO_DOSSIEPJ(ClasseDocumentoFaturamentoDossiePJ.values()),
	CERTIFICADO_MICROEMPREENDEDOR_DOSSIEPJ(ClasseCertificadoMicroempreendedorDossiePJ.values()),
	REQUERIMENTO_EMPRESARIO_DOSSIEPJ(ClasseRequerimentoEmpresarioDossiePJ.values());

	private Object[] listaCampos;

	private ClasseDocumento(Object[] listaCampos) {
		this.listaCampos = listaCampos;
	}

	public Object[] getListaCampos() {
		return listaCampos;
	}

	@Override
	public String toString() {
		return Arrays.toString(this.listaCampos);
	}
}
