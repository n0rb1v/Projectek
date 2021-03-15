package project3;

import java.util.ArrayList;
import java.util.List;

public class AudioFeatures implements Feature {

    private List<String> composer;
    private int length;
    private List<String> performers;
    private String title;

    public AudioFeatures(String title, int length, List<String> performers) {
        noTitleEx(title);
        inLengthEx(length);
        noPerfEx(performers);
        this.length = length;
        this.performers = performers;
        this.title = title;
    }

    public AudioFeatures(String title, int length, List<String> performers, List<String> composer) {
        noTitleEx(title);
        inLengthEx(length);
        noPerfEx(performers);
        this.composer = composer;
        this.length = length;
        this.performers = performers;
        this.title = title;
    }

    @Override
    public List<String> getContributors() {
        List<String> result = new ArrayList<>();
        if (composer != null) {
            for (String item : composer) {
                result.add(item);
            }
        }
        for (String item : performers) {
            result.add(item);
        }
        return result;
    }

    private void noTitleEx(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Missing title");
        }
    }

    private void inLengthEx(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Invalid length");
        }
    }

    private void noPerfEx(List<String> performers) {
        if (performers == null || performers.size() < 1){
            throw new IllegalArgumentException("No performer");
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }
}