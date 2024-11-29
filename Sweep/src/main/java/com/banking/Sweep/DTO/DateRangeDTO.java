package com.banking.Sweep.DTO;


public class DateRangeDTO {
    private String startDate;
    private String endDate;

    public DateRangeDTO() {
    }

    public DateRangeDTO(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
