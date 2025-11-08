package com.empresa.immediate.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;

@Entity
@Builder
@Table(name = "TB_IMMEDIATE")
@SequenceGenerator(name = "seqImmediate", sequenceName = "SQ_TB_IMMEDIATE", allocationSize = 1)
public class ImmediateModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqImmediate")
	@Column(name = "IMMEDIATE_ID", nullable = false)
	private Long id;

	@Column(name = "guid")
	private UUID guid;

	@Column(name = "CLIENT_ID")
	private String client_id;

	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "acct_nr")
	private String acct_nr;

	@Column(name = "qrc_String")
	private String qrc_String;

	@Column(name = "dt_create")
	private Date dt_create;

	@Column(name = "dt_last_upd")
	private Date dt_last_upd;

	@Column(name = "guidapi")
	private String guidapi;

	@Column(name = "codStatus")
	private int codStatus;

	@Column(name = "dt_expiration")
	private Date dt_expiration;

	@Column(name = "acc_token")
	private String acc_token;

	@Column(name = "vlr")
	private String vlr;

	@Column(name = "location")
	private String location;

	@Column(name = "locId")
	private String locId;

	@Column(name = "txid")
	private String txid;

	@Column(name = "dev_cpf")
	private String dev_cpf;

	@Column(name = "dev_cnpj")
	private String dev_cnpj;

	@Column(name = "dev_name")
	private String dev_name;

	@Column(name = "mod_alt")
	private int mod_alt;

	@Column(name = "sol_pag")
	private String sol_pag;

	@Column(name = "revisao")
	private int revisao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "IMMEDIATE_ID")
	private Set<InfoAdicionalModel> addInfo;

	public ImmediateModel() {
		super();

	}

	public ImmediateModel(Long id, UUID guid, String client_id, String cnpj, String acct_nr, String qrc_String,
			Date dt_create, Date dt_last_upd, String guidapi, int codStatus, Date dt_expiration, String acc_token,
			String vlr, String location, String locId, String txid, String dev_cpf, String dev_cnpj, String dev_name,
			int mod_alt, String sol_pag, int revisao, Set<InfoAdicionalModel> addInfo) {
		super();
		this.id = id;
		this.guid = guid;
		this.client_id = client_id;
		this.cnpj = cnpj;
		this.acct_nr = acct_nr;
		this.qrc_String = qrc_String;
		this.dt_create = dt_create;
		this.dt_last_upd = dt_last_upd;
		this.guidapi = guidapi;
		this.codStatus = codStatus;
		this.dt_expiration = dt_expiration;
		this.acc_token = acc_token;
		this.vlr = vlr;
		this.location = location;
		this.locId = locId;
		this.txid = txid;
		this.dev_cpf = dev_cpf;
		this.dev_cnpj = dev_cnpj;
		this.dev_name = dev_name;
		this.mod_alt = mod_alt;
		this.sol_pag = sol_pag;
		this.revisao = revisao;
		this.addInfo = addInfo;
	}

}
