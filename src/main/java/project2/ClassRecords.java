package project2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClassRecords {
    private String className;
    private Random rnd;
    private List<Student> students = new ArrayList<>();

    public ClassRecords(String className, Random rnd) {
        this.className = className;
        this.rnd = rnd;
    }

    public boolean addStudent(Student stu) {
        for (Student item : students) {
            if (item.getName().equals(stu.getName())) {
                return false;
            }
        }
        students.add(stu);
        return true;
    }

    public boolean removeStudent(Student stu) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(stu.getName())){
                students.remove(stu);
                return true;
            }
        }
        return false;
    }

    public double calculateClassAverage() {
        if (students.size()==0){
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }

        int i = 0;
        double sum = 0;
        for (Student item : students) {
            if (item.isMarkEmpty()){
                throw new ArithmeticException("No marks present, average calculation aborted!");
            }
            sum += item.calculateAverage();
            i++;
        }
        return Math.round(sum / i * 100.0) / 100.0;
    }

    public double calculateClassAverageBySubject(Subject sub) {
        if (students.size()==0){
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }
        int i = 0;
        double sum = 0;
        for (Student item : students) {
            double diff = item.calculateSubjectAverage(sub);
            if (diff != 0.0) {
                sum += diff;
                i++;
            }
        }
        return Math.round(sum / i * 100.0) / 100.0;

    }


    public Student findStudentByName(String s) {
        if (s.isEmpty()){
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        if (students.size()==0){
            throw new IllegalStateException("No students to search!");
        }
        Student found = null;
        for (Student item : students) {
            if (item.getName().equals(s)) {
                found = item;
            }
        }
        if (found==null){
            throw new IllegalArgumentException("Student by this name cannot be found! "+s);
        }
        return found;
    }


    public String getClassName() {
        return className;
    }

    public List<Student> getStudents() {
        return students;
    }

    private boolean isEmpty(String s) {
        if ("".equals(s)||s==null) {
            return true;
        }
        return false;
    }

    public String listStudentNames() {
        String stu = "";
        for (Student item : students) {
            stu += item.getName() + ", ";
        }
        return stu.substring(0, stu.length() - 2);
    }

    public Student repetition() {
        if (students.size()==0){
            throw new IllegalStateException("No students to select for repetition!");
        }
        return students.get(rnd.nextInt(students.size()));
    }

    public List<StudyResultByName> listStudyResults() {
        List<StudyResultByName> stures = new ArrayList<>();
        for (Student item: students){
            stures.add(new StudyResultByName(item.getName(), item.calculateAverage()));
        }
        return stures;
    }
}