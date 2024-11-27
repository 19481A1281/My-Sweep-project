package com.banking.Sweep.DTO;

public class SweepDTO {
    private Long sourceAccountNumber;
    private Long destinationAccountNumber;

    public SweepDTO() {
    }

    public SweepDTO(Long sourceAccountNumber, Long destinationAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public Long getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(Long sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public Long getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(Long destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }
}
