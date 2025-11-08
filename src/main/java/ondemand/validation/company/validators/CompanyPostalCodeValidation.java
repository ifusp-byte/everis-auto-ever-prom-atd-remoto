package ondemand.validation.company.validators;

import ondemand.constants.CompanyConstants;
import ondemand.dto.CompanyDto;
import ondemand.dto.ViolationDto;
import ondemand.validation.company.CompanyValidator;
import ondemand.validation.company.service.CompanyValidatorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class CompanyPostalCodeValidation implements CompanyValidator {

    private final CepService cepService;
    private final CompanyValidatorService companyValidatorService;
    private final MessageSource messageSource;

    public CompanyPostalCodeValidation(CepService cepService, MessageSource messageSource, @Lazy CompanyValidatorService companyValidatorService) {
        this.cepService = cepService;
        this.messageSource = messageSource;
        this.companyValidatorService = companyValidatorService;
    }

    @Override
    public Optional<ViolationDto> validate(CompanyDto company) {
        String postalCode = company.getCep();
        if (!Pattern.compile(REGEX_POSTAL_CODE).matcher(postalCode).matches()) {
            boolean statusReturn = true;
            String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_CEP, messageSource);
            return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, postalCode));
        }

        try {
            SearchCepResponse searchCepResponse = cepService.searchCep(postalCode);
            if (StringUtils.isBlank(searchCepResponse.getIbge())) {
                boolean statusReturn = true;
                String violationMessage = Util.getMessageSource(CompanyConstants.VALIDATION_COMPANY_CEP, messageSource);
                return Optional.of(companyValidatorService.newViolation(statusReturn, violationMessage, postalCode));
            } else {
                company.setCodCidade(searchCepResponse.getIbge().substring(TWO));
                company.setCodUf(searchCepResponse.getIbge().substring(ZERO_NUMBER, TWO));
                company.setCodPais(BRASIL_COUNTRY_CODE);
            }

        } catch (SearchCepException | InvalidCepException e) {
            throw new BusinessException(e);
        }

        return Optional.empty();
    }

}
