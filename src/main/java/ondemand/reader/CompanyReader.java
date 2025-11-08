package ondemand.reader;

import ondemand.dto.CompanyDto;
import ondemand.mapper.CompanyMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class CompanyReader {
    Resource companyResource = null;
    private final CompanyMapper companyMapper;

    public CompanyReader(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Bean
    @StepScope
    public PoiItemReader<CompanyDto> companyItemReader(@Value("#{jobExecutionContext['companyFileResource']}") String absolutePathResource) {

        companyResource = new FileSystemResource(absolutePathResource);

        PoiItemReader<CompanyDto> itemReader = new PoiItemReader<>();

        itemReader.setLinesToSkip(4);
        itemReader.setStrict(false);
        itemReader.setResource(companyResource);
        itemReader.setRowMapper(companyMapper);
        itemReader.open(new ExecutionContext());
        itemReader.close();
        return itemReader;

    }
}
