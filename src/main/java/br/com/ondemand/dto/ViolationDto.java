package br.com.ondemand.dto;

public class ViolationDto {
    private Boolean violationStatus;
    private String violationMessage;
    private String violationValue;

    public Boolean getViolationStatus() {
        return violationStatus;
    }

    public void setViolationStatus(Boolean violationStatus) {
        this.violationStatus = violationStatus;
    }

    public String getViolationMessage() {
        return violationMessage;
    }

    public void setViolationMessage(String violationMessage) {
        this.violationMessage = violationMessage;
    }

    public String getViolationValue() {
        return violationValue;
    }

    public void setViolationValue(String violationValue) {
        this.violationValue = violationValue;
    }
}
