package com.banking.Sweep.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SweepStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private Long sweepId;

    @Column(nullable = false)
    private String status;

    private String failureReason;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    public SweepStatus() {
    }

    public SweepStatus(Long statusId, Long sweepId, String status, String failureReason, LocalDateTime timeStamp) {
        this.statusId = statusId;
        this.sweepId = sweepId;
        this.status = status;
        this.failureReason = failureReason;
        this.timeStamp = timeStamp;
    }


    public SweepStatus(Long sweepId, String status, String failureReason, LocalDateTime timeStamp) {
        this.sweepId = sweepId;
        this.status = status;
        this.failureReason = failureReason;
        this.timeStamp = timeStamp;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getSweepId() {
        return sweepId;
    }

    public void setSweepId(Long sweepId) {
        this.sweepId = sweepId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
