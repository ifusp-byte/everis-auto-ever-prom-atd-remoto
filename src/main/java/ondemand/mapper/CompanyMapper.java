package ondemand.mapper;

import ondemand.dto.CompanyDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CompanyMapper implements RowMapper<CompanyDto> {
    private static final int ONE = 1;

    @Override
    public CompanyDto mapRow(RowSet rowSet) throws Exception {

        CompanyDto companyDto = new CompanyDto();
        AtomicInteger colm = new AtomicInteger(ONE);

        companyDto.setCnpj(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setCpf(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setRazaoSocial(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setFantasia(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setInscricaoEstadual(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setInscricaoMunicipal(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setTipoContribuinte(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsClienteFinal(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setEmail(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setDddCelular(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setTelefoneCelular(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setDddComercial(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setTelefoneComercial(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setEndereco(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setNumero(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setComplemento(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setBairro(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setCidade(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setSiglaEstado(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setCep(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setObservacoes(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsCliente(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsFornecedor(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsFuncionario(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsRepresentante(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsTransportadora(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        companyDto.setIsTransportadora(StringUtils.EMPTY);

        if (rowSet.getCurrentRow().length == TWENTY_EIGHT) {
            companyDto.setIsTransportadora(rowSet.getCurrentRow()[Integer.parseInt(String.valueOf(colm.incrementAndGet()))].replaceAll(REGEX_ONLY_INVISIBLE_CHAR, StringUtils.EMPTY).trim());
        }
        return companyDto;
    }
}
