
package com.company.wextag.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PurchaseTransaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "TB_PURCHASE_TRANSACTION")
public class PurchaseTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TRANSACTION_DATE")
	private LocalDate transactionDate;

	@Column(name = "PURCHASE_AMOUNT")
	private BigDecimal purchaseAmount;

}
