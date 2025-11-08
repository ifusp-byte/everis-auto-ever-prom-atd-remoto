package br.com.ondemand.mapper;

import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.RowSet;
import javax.swing.tree.RowMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.com.ondemand.dto.ProductDto;

@Component
public class ProductMapper implements RowMapper<ProductDto> {
	private static final int ZERO = 0;

	@Override
	public ProductDto mapRow(RowSet rowSet) throws Exception {

		ProductDto productDto = new ProductDto();
		AtomicInteger colm = new AtomicInteger(ZERO);

		productDto.setCodInterno(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
				.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		productDto.setCodBarras(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
				.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		productDto.setDescricao(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
				.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		productDto.setNcm(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
				.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		productDto.setUnidade(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
				.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		productDto.setPrecoUnitario(StringUtils.EMPTY);

		if (rowSet.getCurrentRow().length == SIX) {
			productDto.setPrecoUnitario(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.getAndIncrement()))]
					.replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
		}
		return productDto;
	}

}
