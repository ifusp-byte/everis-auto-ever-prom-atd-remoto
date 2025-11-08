package com.company.wextag.dto;

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
@XmlRootElement(name = "ConvertPurchaseTransactionOutputDTO")
public class ConvertPurchaseTransactionOutputDTO {

	private String exchangeRate;
	private LocalDate recordDate;
	private ConvertedPurchaseTransactionOutputDTO convertedPurchaseTransaction;

}
