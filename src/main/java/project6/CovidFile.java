package project6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CovidFile {
    private List<Citizen> citizens = new ArrayList<>();

    public void addCitizen(Citizen c) {
        citizens.add(c);
    }

    public List<Citizen> getCitizens() {
        return new ArrayList<>(citizens);
    }

    public void addFromFile(Path file) {
        try (BufferedReader bf = Files.newBufferedReader(file)) {
            String line = bf.readLine();
            while ((line = bf.readLine()) != null) {
                String[] result = line.split(";");
                Citizen c = new Citizen(
                        result[0],
                        result[1],
                        Integer.parseInt(result[2]),
                        result[3],
                        result[4]
                );
                citizens.add(c);
            }
        } catch (IOException e) {
            throw new IllegalStateException("file error");
        }
    }

    public void saveFile(Path file, List<Citizen> cl) {
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            LocalTime time = LocalTime.of(8,0);
            for (Citizen item : cl) {
                bw.write(
                        time + ";" +
                                item.getName() + ";" +
                                item.getPostcode() + ";" +
                                item.getAge() + ";" +
                                item.getEmail() + ";" +
                                item.getTAJnumber()+"\n");
                time = time.plusMinutes(30);
            }

        } catch (IOException ioe) {
            throw new IllegalStateException("Can not write file", ioe);
        }
    }
}