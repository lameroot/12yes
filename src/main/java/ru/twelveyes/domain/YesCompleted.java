package ru.twelveyes.domain;

import java.util.Date;

/**
 * Created by lameroot on 11.01.14.
 */
public class YesCompleted {

    private String reportDescription;
    private Company company;
    private Date completedAt = new Date();
    private Double completedBudget;

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Double getCompletedBudget() {
        return completedBudget;
    }

    public void setCompletedBudget(Double completedBudget) {
        this.completedBudget = completedBudget;
    }
}
