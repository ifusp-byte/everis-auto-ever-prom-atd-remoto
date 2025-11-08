
package com.company.wextag.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PurchaseTransactionOutputDTO")
public class PurchaseTransactionOutputDTO {

	private Long id;
	private String description;
	private LocalDate transactionDate;
	private BigDecimal purchaseAmount;

}
