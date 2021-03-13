package project1;

import java.util.Scanner;

public class Controller {
    private Office office = new Office();
    private Scanner scanner = new Scanner(System.in);

    private void readOffice() {
        System.out.println("Targyalo: neve,szelessege,hossza (ures sor vege)");
        String c;
        do {
            c = scanner.nextLine();
            if (!c.equals("")) {
                String[] arrOfString = c.split(",");
                MeetingRoom room = new MeetingRoom(arrOfString[0], Integer.parseInt(arrOfString[1]), Integer.parseInt(arrOfString[2]));
                office.addMeetingRoom(room);
            }
        }
        while (!c.isEmpty());
    }

    private int printMenu() {
        System.out.println(
                "-----------------------------------------------------------------\n"+
                        "| 1. Targyalok sorrendben       | 5. Kereses nev alapjan        |\n"+
                        "| 2. Targyalok visz. sorrendben | 6. Kereses nevtoredek alapjan |\n"+
                        "| 3. Minden masodik targyalo    | 7. Kereses terulet alapjan    |\n"+
                        "| 4. Teruletek                  | 8. Kilepes                    |\n"+
                        "-----------------------------------------------------------------\n"+
                        "Menupont:");
        return Integer.parseInt(stringIN());
    }

    private void runMenu(boolean run) {
        while (run == true){
            switch (printMenu()){
                case 1:
                    office.printNames();
                    break;
                case 2:
                    office.printNamesReverse();
                    break;
                case 3:
                    office.printEvenNames();
                    break;
                case 4:
                    office.printAreas();
                    break;
                case 5:
                    office.printMeetingRoomsWithName(stringIN());
                    break;
                case 6:
                    office.printMeetingContains(stringIN());
                    break;
                case 7:
                    office.printAreasLargerThan(Integer.parseInt(stringIN()));
                    break;
                case 8:
                    run = false;
            }
        }
    }

    private String stringIN(){
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.readOffice();
        c.runMenu(true);

    }
}
