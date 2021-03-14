package project5;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Company {
    private InputStream employeesFile;
    private InputStream projectsFile;
    private List<Employee> employees;
    private List<Project> projects;
    private List<TimeSheetItem> timeSheetItems;

    public Company(InputStream employeesFile, InputStream projectsFile) {
        this.employeesFile = employeesFile;
        this.projectsFile = projectsFile;
    }

    public void addTimeSheetItem(Employee e, Project p, LocalDateTime begin, LocalDateTime end) {
        timeSheetItems.add(new TimeSheetItem(e, p, begin, end));
    }

    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {
        return null;
    }

    public void printToFile(String employeeName, int year, int month, Path file) {

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



