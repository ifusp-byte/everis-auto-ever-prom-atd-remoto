package br.com.ondemand.service;

import java.util.List;

import br.com.ondemand.dto.CompanyDto;

public interface CompanyImportService {
    Boolean companyJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon);

    void allCompanyItems(List<CompanyDto> companyList);
}
