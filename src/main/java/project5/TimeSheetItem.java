package project5;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class TimeSheetItem {
    private Employee employee;
    private Project project;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    public TimeSheetItem(Employee employee, Project project, LocalDateTime beginDate, LocalDateTime endDate) {
        if (endDate.isBefore(beginDate)) {
            throw new IllegalArgumentException("error in date");
        }
        if (!beginDate.toLocalDate().equals(endDate.toLocalDate())) {
            throw new IllegalArgumentException("not same date");
        }
        this.employee = employee;
        this.project = project;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public long hoursBetweenDates() {
        return Duration.between(beginDate, endDate).toHours();
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
