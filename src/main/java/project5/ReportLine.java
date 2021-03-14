package project5;

public class ReportLine {
    private Project project;
    private long time;

    public ReportLine(Project project, long time) {
        this.project = project;
        this.time = time;
    }

    public Project getProject() {
        return project;
    }

    public long getTime() {
        return time;
    }
}
