package br.gov.caixa.siavl.atendimentoremoto.constants;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.UTILITY_CLASS;

@SuppressWarnings("all")
public class MessageConstants {

	private MessageConstants() {
		throw new IllegalStateException(UTILITY_CLASS);
	}

	// classe
	// AtendimentoRemotoException
	public static final String EXCEPTION_ERRO_TOKEN = "exception.erro.token";
	public static final String EXCEPTION_ERRO_DADOS_REQUISICAO = "exception.erro.dados.requisicao";
	public static final String EXCEPTION_ERRO_INTERNO = "exception.erro.interno";

	// package
	// br.gov.caixa.siavl.atendimentoremoto.validation.crianota.validators.formato
	public static final String VALIDATOR_FORMATO_CPF_CNPJ = "validator.formato.cpf.cnpj";
	public static final String VALIDATOR_FORMATO_CPF_SOCIO = "validator.formato.cpf.socio";
	public static final String VALIDATOR_FORMATO_EQUIPE = "validator.formato.equipe";
	public static final String VALIDATOR_FORMATO_MODELO = "validator.formato.modelo";
	public static final String VALIDATOR_FORMATO_PRODUTO = "validator.formato.produto";
	public static final String VALIDATOR_FORMATO_QUANTIDADE_META = "validator.formato.quantidade.meta";
	public static final String VALIDATOR_FORMATO_TIPO_DOCUMENTO = "validator.formato.tipo.documento";
	public static final String VALIDATOR_FORMATO_VALOR_META = "validator.formato.valor.meta";

	// package
	// br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos
	public static final String CAMPOS_DINAMICOS = "campos.dinamicos";
	public static final String VALIDATOR_DINAMICOS_FILTRO_QUANTIDADE_DINAMICOS = "validator.dinamicos.filtro.quantidade.dinamicos";
	public static final String VALIDATOR_DINAMICOS_FILTRO_NUMERO = "validator.dinamicos.filtro.numero";
	public static final String VALIDATOR_DINAMICOS_FILTRO = "validator.dinamicos.filtro";
	public static final String VALIDATOR_DINAMICOS_OBRIGATORIO = "validator.dinamicos.obrigatorio";
	public static final String VALIDATOR_DINAMICOS_TAMANHO = "validator.dinamicos.tamanho";
	public static final String VALIDATOR_DINAMICOS_FORMATO = "validator.dinamicos.formato";
	public static final String VALIDATOR_DINAMICOS_MASCARA = "validator.dinamicos.mascara";

	// classe
	// CriaNotaServiceImpl
	public static final String NOTA_SUCESSO = "nota.sucesso";
	public static final String NOTA_ERRO_CAMPOS_OBRIGATORIO = "nota.erro.obrigatorio";
	public static final String NOTA_ERRO_CAMPOS_FORMATO = "nota.erro.formato";
	public static final String NOTA_ERRO_CAMPOS_DINAMICOS = "nota.erro.campos.dinamicos";
	
	public static final String CONTRATA_NOTA_SUCESSO = "contrata.nota.sucesso";

	// classe ContrataNotaObrigatorioFormato
	// package br.gov.caixa.siavl.atendimentoremoto.validation.contratanota.validators
	public static final String CONTRATA_NOTA_OBRIGATORIO_ERRO = "contrata.nota.obrigatorio.erro";
	public static final String CONTRATA_NOTA_OBRIGATORIO_NUMERO_NOTA = "contrata.nota.obrigatorio.numero.nota";
	public static final String CONTRATA_NOTA_OBRIGATORIO_VALOR = "contrata.nota.obrigatorio.valor";
	public static final String CONTRATA_NOTA_OBRIGATORIO_CPF_CNPJ = "contrata.nota.obrigatorio.cpf.cnpj";
	public static final String CONTRATA_NOTA_FORMATO_NUMERO_NOTA = "contrata.nota.formato.numero.nota";
	public static final String CONTRATA_NOTA_FORMATO_VALOR = "contrata.nota.formato.valor";
	public static final String CONTRATA_NOTA_FORMATO_CPF = "contrata.nota.formato.cpf";
	public static final String CONTRATA_NOTA_FORMATO_CNPJ = "contrata.nota.formato.cnpj";	
	public static final String CONTRATA_NOTA_FILTRO_ERRO = "contrata.nota.filtro.erro";
	public static final String CONTRATA_NOTA_FILTRO_NUMERO_NOTA = "contrata.nota.filtro.numero.nota";
	public static final String CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA = "contrata.nota.filtro.origem.situacao.nota";
	public static final String CONTRATA_NOTA_FILTRO_ORIGEM_SITUACAO_NOTA_VALORES = "contrata.nota.filtro.origem.situacao.nota.valores";
	public static final String CONTRATA_NOTA_FILTRO_CPF_CNPJ = "contrata.nota.filtro.cpf.cnpj.nota";
	public static final String CONTRATA_NOTA_FILTRO_CPF_CNPJ_VALORES = "contrata.nota.filtro.cpf.cnpj.nota.valores";
	public static final String CONTRATA_NOTA_FILTRO_VALOR = "contrata.nota.filtro.cpf.valor";
	public static final String CONTRATA_NOTA_FILTRO_VALOR_VALORES = "contrata.nota.filtro.cpf.valor.valores";
	public static final String CONTRATA_NOTA_TIPO_DOCUMENTO_CNPJ = "contrata.nota.tipo.documento.cnpj";
	public static final String CONTRATA_NOTA_TIPO_DOCUMENTO_CPF = "contrata.nota.tipo.documento.cpf";
	public static final String CONTRATA_NOTA_TIPO_DOCUMENTO_OBRIGATORIO = "contrata.nota.tipo.documento.obrigatorio";
	public static final String CONTRATA_NOTA_TIPO_DOCUMENTO_FORMATO = "contrata.nota.tipo.documento.formato";

}
