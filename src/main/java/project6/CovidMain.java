package project6;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CovidMain {
    Scanner scanner = new Scanner(System.in);
    Map<String, String> zipMap;
    Citizen citizen;

    public void runMenu(CovidFile cf, CovidDao cd) {
        int menu = 0;
        zipMap = cd.mapPostcodes();

        while (!(menu == 7)) {
            System.out.println("" +
                    "1. Regisztráció\n" +
                    "2. Tömeges regisztráció\n" +
                    "3. Generálás\n" +
                    "4. Oltás\n" +
                    "5. Oltás meghiúsulás\n" +
                    "6. Riport\n" +
                    "7. Kilépés\n" +
                    "Menupont:");

            menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    regisztracio(cd);
                    break;
                case 2:
                    cf.addFromFile(filePath());
                    cd.saveCitizenList(cf.getCitizens());
                    break;
                case 3:
                    generalas(cf, cd);
                    break;
                case 4:
                    oltas(cd);
                    break;
                case 5:
                    oltashiusulas(cd);
                    break;
                case 6:
                    riport(cd);
                    break;
                case 7:

            }
        }
    }

    private void riport(CovidDao cd) {
        List<Citizen> citizens = cd.selectCitizenZip(zipIN());
        int sum0 = 0;
        int sum1 = 0;
        int sum2 = 0;
        for (Citizen item : citizens) {
            if (item.getVaccNumber() == 0) {
                sum0++;
            }
            if (item.getVaccNumber() == 1) {
                sum1++;
            }
            if (item.getVaccNumber() == 2) {
                sum2++;
            }
        }
        System.out.println("oltatlan: "+sum0+", egy oltas: "+sum1+", ket oltas: "+sum2);
    }

    private void regisztracio(CovidDao cd) {
        System.out.println("Teljes név:");
        String name = scanner.nextLine();
        System.out.println("Irányítószám:");
        String post = scanner.nextLine();
        System.out.println(zipMap.get(post));
        System.out.println("Életkor:");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("E-mail cím:");
        String email1 = scanner.nextLine();
        System.out.println("E-mail cím meg1x:");
        String email2 = scanner.nextLine();
        System.out.println("TAJ száma:");
        String taj = scanner.nextLine();
        Citizen c = null;
        try {
            if (!email1.equals(email2)) {
                throw new IllegalArgumentException("hibas email");
            }
            c = new Citizen(name, post, age, email1, taj);
        } catch (IllegalArgumentException e) {
            System.out.println("hibás adatok: " + e);
        }
        cd.saveCitizen(c);
    }

    private void generalas(CovidFile cf, CovidDao cd) {
        List<Citizen> generated = cd.listGenerate(zipIN());
        cf.saveFile(filePath(), generated);
    }

    private void oltas(CovidDao cd) {
        String taj = tajIN();
        if (isValidTAJ(taj)) {
            citizen = cd.selectCitizenTaj(taj);
        } else {
            System.out.println("hibas taj");
        }
        if (citizen != null) {
            LocalDateTime lastdate = dateIN();
            Vaccination vacc = null;
            switch (citizen.getVaccNumber()) {
                case 0:
                    citizen.setVaccNumber(1);
                    citizen.setVaccLast(lastdate);
                    String status = "sikeres";
                    vacc = new Vaccination(0, citizen.getId(), Timestamp.valueOf(lastdate), status, noteIN(), vaccIN());
                    cd.updateCitizen(citizen, vacc);
                    break;
                case 1:
                    citizen.setVaccNumber(2);
                    citizen.setVaccLast(lastdate);
                    vacc = cd.selectVaccination(citizen.getId());
                    System.out.println("Oltas: " + vacc.getVacc_type() + " Time: " + vacc.getVaccination_date());
                    scanner.nextLine();
                    vacc.setNote(noteIN());
                    if (vacc.getVaccination_date().plusDays(15).isBefore(lastdate)) {
                        vacc.setVaccination_date(lastdate);
                        cd.updateCitizen(citizen, vacc);
                    } else {
                        System.out.println("Meg nem telt el 15 nap!");
                    }
                    break;
                case 2:
                default:
                    System.out.println("Mar 2x beoltasra kerult!");
                    scanner.nextLine();
            }
        } else {
            System.out.println("nem regisztralt taj");
        }
    }

    private void oltashiusulas(CovidDao cd) {
        String taj = tajIN();
        if (isValidTAJ(taj)) {
            citizen = cd.selectCitizenTaj(taj);
        } else {
            System.out.println("hibas taj");
        }
        if (citizen != null) {
            LocalDateTime lastdate = dateIN();
            citizen.setVaccLast(lastdate);
            String status = "sikertelen";
            System.out.print("Meghiusulas oka:");
            Vaccination vc = new Vaccination(0, citizen.getId(), Timestamp.valueOf(lastdate), status, noteIN(), null);
            cd.updateCitizen(citizen, vc);
        }

    }

    private LocalDateTime dateIN() {
        System.out.println("Datum:");
        String date = scanner.nextLine();
        LocalDateTime lastdate = null;
        if (date.isEmpty()) {
            lastdate = LocalDateTime.now();
        } else {
            lastdate = LocalDateTime.parse(date);
        }
        return lastdate;
    }

    private String zipIN() {
        System.out.println("Iranyito szam:");
        return scanner.nextLine();

    }

    private String vaccIN() {
        System.out.println("Vacc tipus:");
        return scanner.nextLine();
    }

    private String noteIN() {
        System.out.println("Note:");
        return scanner.nextLine();
    }

    private String tajIN() {
        System.out.println("TAJ szam:");
        return scanner.nextLine();
    }

    private boolean isValidTAJ(String s) {
        int sum = 0;
        for (int i = 0; i < 8; i += 2) {
            sum += (Integer.parseInt(String.valueOf(s.charAt(i))) * 3);
            sum += (Integer.parseInt(String.valueOf(s.charAt(i + 1))) * 7);
        }
        if (!(sum % 10 == Integer.parseInt(String.valueOf(s.charAt(8))))) {
            return false;
        }
        return true;
    }

    private Path filePath() {
        System.out.println("File:");
        String file = scanner.nextLine();
        return Path.of("src/main/resources/" + file);

    }


    public static void main(String[] args) {
        MariaDbDataSource dataSource;

        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/covid?useUnicode=true");
            dataSource.setUser("covid");
            dataSource.setPassword("covid");

        } catch (SQLException e) {
            throw new IllegalStateException("sql database error", e);
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        CovidMain cm = new CovidMain();
        CovidFile covidFile = new CovidFile();
        CovidDao covidDao = new CovidDao(dataSource);
        cm.runMenu(covidFile, covidDao);
    }
}