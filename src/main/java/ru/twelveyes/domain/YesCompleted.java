package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 11.01.14.
 */
@Embeddable
public class YesCompleted {

    @Column(name = "report_description")
    private String reportDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_company_id",nullable = true)
    private Company company;
    @Column(name = "completed_at",nullable = false)
    private Date completedAt = new Date();
    @Column(name = "completed_budget")
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
