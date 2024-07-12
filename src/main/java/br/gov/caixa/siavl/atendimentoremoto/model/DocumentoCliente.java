package br.gov.caixa.siavl.atendimentoremoto.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DocumentoClienteId.class)
@Entity(name = "DocumentoCliente")
@Table(name = "AVLTB033_DOCUMENTO_CLIENTE", schema = "AVL")
public class DocumentoCliente {
	
	@Id
	@Column(name = "TS_INCLUSAO_DOCUMENTO")
	private Date inclusaoDocumento;

	@Id
	@Column(name = "IC_TIPO_PESSOA", columnDefinition = "CHAR(2)")
	private String tipoPessoa;

	@Id
	@Column(name = "NU_CPF_CNPJ_CLIENTE")
	private Long cpfCnpjCliente;

	@Id
	@Column(name = "NU_TIPO_DOCUMENTO_CLIENTE")
	private Long tipoDocumentoCliente;

	@Column(name = "NU_MATRICULA_ATENDENTE")
	private Long matriculaAtendente;

	@Column(name = "IC_SITUACAO_DOCUMENTO_CLIENTE")
	private Long situacaoDocumentoCliente;

	@Column(name = "IC_STCO_DOCUMENTO_CLIENTE")
	private Long stcoDocumentoCliente;

	@Column(name = "TS_VALIDACAO_DOCUMENTO")
	private Date validacaoDocumento;

	@Column(name = "CO_GED_ANEXO")
	private String codGED;

	@Column(name = "NO_ANEXO")
	private String nomeAnexo;

	@Column(name = "CO_MIMETYPE_ANEXO")
	private String mimetypeAnexo;

	@Column(name = "CO_EXTENSAO_ANEXO")
	private String extensaoAnexo;
	
	@Column(name = "VR_ARQUIVO_RECEBIDO")
	private Blob arquivoRecebido;

}
