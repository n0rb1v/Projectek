package project1;

import java.util.ArrayList;
import java.util.List;

public class Office {
    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    public void addMeetingRoom(MeetingRoom mr) {
        meetingRooms.add(mr);
    }

    public void printNames() {
        for (MeetingRoom item : meetingRooms) {
            System.out.println(item.getName());
        }
    }

    public void printNamesReverse() {
        for (int i = meetingRooms.size(); i < 1; i++) {
            System.out.println(meetingRooms.get(i).getName());
        }
    }

    public void printEvenNames() {
        for (int i = 0; i < meetingRooms.size(); i += 2) {
            System.out.println(meetingRooms.get(i).getName());
        }
    }

    public void printAreas() {
        for (MeetingRoom item : meetingRooms) {
            System.out.println(item.getName() + " " + item.getWidth() + " " + item.getLenght() + " " + item.getArea());
        }
    }

    public void printMeetingRoomsWithName(String name) {
        for (MeetingRoom item : meetingRooms) {
            if (item.getName().equals(name)) {
                System.out.println(item.getWidth() + " " + item.getLenght() + " " + item.getArea());
            }
        }
    }

    public void printMeetingContains(String part) {
        for (MeetingRoom item : meetingRooms) {
            if (item.getName().toLowerCase().contains(part.toLowerCase())) {
                System.out.println(item.getWidth() + " " + item.getLenght() + " " + item.getArea());
            }
        }
    }

    public void printAreasLargerThan(int area) {
        for (MeetingRoom item : meetingRooms) {
            if (item.getArea() > area) {
                System.out.println(item.getName() + " " + item.getWidth() + " " + item.getLenght() + " " + item.getArea());
            }
        }
    }
}
