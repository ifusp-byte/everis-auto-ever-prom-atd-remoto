package ondemand.dto;

import java.util.List;

public class CompanyItemDto {
    private int lineNumber;
    private List<ViolationDto> violations;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public List<ViolationDto> getViolations() {
        return violations;
    }

    public void setViolations(List<ViolationDto> violations) {
        this.violations = violations;
    }
}
