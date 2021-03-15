package project2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SchoolRecordsController {

    private ClassRecords classRecords;
    private Tutor tutor = new Tutor("Nagy Csilla",
            Arrays.asList(new Subject("földrajz"),
                    new Subject("matematika"),
                    new Subject("biológia"),
                    new Subject("zene"),
                    new Subject("fizika"),
                    new Subject("kémia")));

    public void setUp() {
        classRecords = new ClassRecords("Fourth Grade A", new Random(5));
        Student firstStudent = new Student("Kovacs Rita");
        Student secondStudent = new Student("Nagy Bela");
        Student thirdStudent = new Student("Varga Marton");
        firstStudent.grading(new Mark(MarkType.A, new Subject("foldrajz"), tutor));
        firstStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        firstStudent.grading(new Mark(MarkType.D, new Subject("foldrajz"), tutor));
        secondStudent.grading(new Mark(MarkType.A, new Subject("biologia"), tutor));
        secondStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        secondStudent.grading(new Mark(MarkType.D, new Subject("zene"), tutor));
        thirdStudent.grading(new Mark(MarkType.A, new Subject("fizika"), tutor));
        thirdStudent.grading(new Mark(MarkType.C, new Subject("kémia"), tutor));
        thirdStudent.grading(new Mark(MarkType.D, new Subject("foldrajz"), tutor));
        classRecords.addStudent(firstStudent);
        classRecords.addStudent(secondStudent);
        classRecords.addStudent(thirdStudent);
    }

    boolean exit = false;

    public void printMenu() {
        System.out.println(
                "----------------------------------------------------------------------------------\n" +
                        "| 1. Diákok nevének listázása   |  6. Osztályátlag kiszámolása                   |\n" +
                        "| 2. Diák név alapján keresése  |  7. Tantárgyi átlag kiszámolása                |\n" +
                        "| 3. Diák létrehozása           |  8. Diákok átlagának megjelenítése             |\n" +
                        "| 4. Diák név alapján törlése   |  9. Diák átlagának kiírása                     |\n" +
                        "| 5. Diák feleltetése           | 10. Diák tantárgyhoz tartozó átlagának kiírása |\n" +
                        "------------------------------  11. KILÈPÈS  -------------------------------------\n" +
                        "Menupont:");
    }

    public void runMenu() {

        int menu = 0;
        String nev = "";
        String targy = "";
        MarkType jegy = MarkType.A;
        Subject subj = null;
        Student felel = null;
        Tutor okt = null;

        Scanner scanner = new Scanner(System.in);
        menu = Integer.parseInt(scanner.nextLine());

        switch (menu) {
            case 1:
                System.out.println(classRecords.listStudentNames());
                break;
            case 2:
                System.out.println("Nev:");
                nev = scanner.nextLine();
                System.out.println(classRecords.findStudentByName(nev));
                break;
            case 3:
                System.out.println("Nev:");
                nev = scanner.nextLine();
                classRecords.addStudent(new Student(nev));
                break;
            case 4:
                System.out.println("Nev:");
                nev = scanner.nextLine();
                classRecords.removeStudent(classRecords.findStudentByName(nev));
                break;
            case 5:
                System.out.println("Jegy:");
                jegy = MarkType.valueOf(scanner.nextLine());
                System.out.println("Targy:");
                targy = scanner.nextLine();
                for (int i = 0; i < classRecords.getStudents().size(); i++) {
                    for (int j = 0; j < classRecords.getStudents().get(i).getMarks().size(); j++) {
                        if (classRecords.getStudents().get(i).getMarks().get(j).getSubject().getSubjectName().equals(targy)) {
                            subj = classRecords.getStudents().get(i).getMarks().get(j).getSubject();
                        }
                    }
                }
                System.out.println("Oktato:");
                nev = scanner.nextLine();
                if (tutor.getName().equals(nev)) {
                    okt = tutor;
                    felel = classRecords.repetition();
                    felel.grading(new Mark(jegy, subj, okt));
                }
                break;
            case 6:
                System.out.println(classRecords.calculateClassAverage());
                break;
            case 7:
                System.out.println("Targy:");
                targy = scanner.nextLine();
                for (int i = 0; i < classRecords.getStudents().size(); i++) {
                    for (int j = 0; j < classRecords.getStudents().get(i).getMarks().size(); j++) {
                        if (classRecords.getStudents().get(i).getMarks().get(j).getSubject().getSubjectName().equals(targy)) {
                            subj = classRecords.getStudents().get(i).getMarks().get(j).getSubject();
                        }
                    }
                }
                System.out.println(classRecords.calculateClassAverageBySubject(subj));
                break;
            case 8:
                System.out.println(classRecords.listStudyResults());
                break;
            case 9:
                System.out.println("Nev:");
                nev = scanner.nextLine();
                System.out.println(classRecords.findStudentByName(nev).calculateAverage());
                break;
            case 10:
                System.out.println("Nev:");
                nev = scanner.nextLine();
                System.out.println("Targy:");
                targy = scanner.nextLine();
                for (int i = 0; i < classRecords.getStudents().size(); i++) {
                    for (int j = 0; j < classRecords.getStudents().get(i).getMarks().size(); j++) {
                        if (classRecords.getStudents().get(i).getMarks().get(j).getSubject().getSubjectName().equals(targy)) {
                            subj = classRecords.getStudents().get(i).getMarks().get(j).getSubject();
                        }
                    }
                }
                System.out.println(classRecords.findStudentByName(nev).calculateSubjectAverage(subj));
                break;
            case 11:
                exit = true;
        }

    }


    public static void main(String[] args) {
        SchoolRecordsController schoolRecordsController = new SchoolRecordsController();
        schoolRecordsController.setUp();

        while (!schoolRecordsController.exit) {
            schoolRecordsController.printMenu();
            schoolRecordsController.runMenu();
        }

    }
}