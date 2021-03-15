package project6;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Citizen {
    private long id;
    private String name;
    private String postcode;
    private int age;
    private String email;
    private String TAJnumber;
    private int vaccNumber;
    private LocalDateTime vaccLast;

    public Citizen(String name, String postcode, int age, String email, String TAJnumber) {
        this.name = isEmpty(name);
        this.postcode = isEmpty(postcode);
        this.age = isValidAge(age);
        this.email = isValidEmail(email);
        this.TAJnumber = isValidTAJ(TAJnumber);
    }

    public int getVaccNumber() {
        return vaccNumber;
    }

    public LocalDateTime getVaccLast() {
        return vaccLast;
    }

    public void setVaccLast(LocalDateTime vaccLast) {
        this.vaccLast = vaccLast;
    }

    public void setVaccNumber(int vaccNumber) {
        this.vaccNumber = vaccNumber;
    }

    public Citizen(long id, String name, String postcode, int age, String email, String TAJnumber, int vaccNumber, Timestamp vaccLast) {
        this.id = id;
        this.name = name;
        this.postcode = postcode;
        this.age = age;
        this.email = email;
        this.TAJnumber = TAJnumber;
        this.vaccNumber = vaccNumber;
        if (vaccLast != null) {
            this.vaccLast = vaccLast.toLocalDateTime();
        }
    }

    private String isEmpty(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("empty parameter");
        }
        return s;
    }

    private String isValidEmail(String s) {
        if (!(s.length() > 3 && s.contains("@"))) {
            throw new IllegalArgumentException("email not valid");
        }
        return s;
    }

    private int isValidAge(int i) {
        if (!(i > 10 && i < 150))
            throw new IllegalArgumentException("age not valid");
        return i;
    }

    private String isValidTAJ(String s) {
        int sum = 0;
        for (int i = 0; i < 8; i += 2) {
            sum+=(Integer.parseInt(String.valueOf(s.charAt(i)))*3);
            sum+=(Integer.parseInt(String.valueOf(s.charAt(i+1)))*7);
        }
        if (!(sum % 10 == Integer.parseInt(String.valueOf(s.charAt(8))))){
            throw new IllegalArgumentException("invalid TAJ");
        }
        return s;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getTAJnumber() {
        return TAJnumber;
    }
}