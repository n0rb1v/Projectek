package project5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company {
    private InputStream employeesFile;
    private InputStream projectsFile;
    private List<Employee> employees;
    private List<Project> projects;
    private List<TimeSheetItem> timeSheetItems = new ArrayList<>();

    public Company(InputStream employeesFile, InputStream projectsFile) {
        this.employeesFile = employeesFile;
        this.projectsFile = projectsFile;
    }

    public void addTimeSheetItem(Employee e, Project p, LocalDateTime begin, LocalDateTime end) {
        timeSheetItems.add(new TimeSheetItem(e, p, begin, end));
    }

    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {
        try {
            if (!getEmployees().contains(new Employee(employeeName.split(" ")[0], employeeName.split(" ")[1]))) {
                throw new IllegalArgumentException("hibas nev");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("hibas nev");
        }
        List<ReportLine> result = new ArrayList<>();
        Map<String, Long> temp = new HashMap<>();
        for (TimeSheetItem item : timeSheetItems) {
            if (item.getEmployee().getName().equals(employeeName) &
                    item.getBeginDate().getYear() == year &
                    item.getBeginDate().getMonthValue() == month) {
                String key = item.getProject().getName();
                if (!temp.containsKey(key)) {
                    temp.put(key, 0L);
                }
                temp.put(key, temp.get(key) + item.hoursBetweenDates());
            }
        }
        for (String item : temp.keySet()) {
            result.add(new ReportLine(new Project(item), temp.get(item)));
        }
        return result;
    }

    public void printToFile(String employeeName, int year, int month, Path file) {
        List<ReportLine> result = calculateProjectByNameYearMonth(employeeName, year, month);
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        for (ReportLine item : result) {
            sb.append(item.getProject().getName() + "\t" + item.getTime() + "\n");
            sum += item.getTime();
        }
        String body = employeeName + "\t" + year + "-" + String.format("%02d",month)+ "\t" + sum + "\n" + sb.toString();
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write(body);
        } catch (IOException e) {
            throw new IllegalStateException("file error");
        }
    }

    private List<Employee> getEmployeesFile() {
        List<Employee> result = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(employeesFile))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] tm = line.split(" ");
                result.add(new Employee(tm[0], tm[1]));
            }
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("file error");
        }
    }

    private List<Project> getProjectsFile() {
        List<Project> result = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(projectsFile))) {
            String line;
            while ((line = bf.readLine()) != null) {
                result.add(new Project(line));
            }
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("file error");
        }
    }

    public List<Employee> getEmployees() {
        if (employees == null) {
            employees = getEmployeesFile();
            return this.employees;
        } else {
            return this.employees;
        }
    }

    public List<Project> getProjects() {
        if (projects == null) {
            projects = getProjectsFile();
            return this.projects;
        } else {
            return this.projects;
        }
    }
}



