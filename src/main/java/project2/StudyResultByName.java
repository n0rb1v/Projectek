package project2;

public class StudyResultByName {
    String studentName;
    double StudyAverage;

    public StudyResultByName(String studentName, double studyAverage) {
        this.studentName = studentName;
        this.StudyAverage = studyAverage;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getStudyAverage() {
        return StudyAverage;
    }

    @Override
    public String toString() {
        return studentName + ":"+ StudyAverage;
    }
}