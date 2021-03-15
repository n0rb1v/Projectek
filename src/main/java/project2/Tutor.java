package project2;

import java.util.ArrayList;
import java.util.List;

public class Tutor {
    private String name;
    private List<Subject> taughtSubjects = new ArrayList<>();

    public Tutor(String name, List<Subject> taughtSubjects) {
        this.name = name;
        this.taughtSubjects = taughtSubjects;
    }

    public String getName() {
        return name;
    }

    public boolean tutorTeachingSubject(Subject subject) {
        for (Subject item : taughtSubjects) {

            String name1 = item.getSubjectName();
            String name2 = subject.getSubjectName();

            if (name1.equals(name2)) {
                return true;
            }
        }
        return false;
    }
}