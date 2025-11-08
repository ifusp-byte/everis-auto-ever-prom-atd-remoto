package ondemand.service;

import ondemand.dto.CompanyDto;
import java.util.List;

public interface CompanyImportService {
    Boolean companyJob(String userEmail, String userPersistenceUnit, String urlFileAmazon, Long idFileAmazon);

    void allCompanyItems(List<CompanyDto> companyList);
}
