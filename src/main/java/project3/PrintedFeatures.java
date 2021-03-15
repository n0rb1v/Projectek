package project3;

import java.util.ArrayList;
import java.util.List;

public class PrintedFeatures implements Feature {

    private List<String> authors;
    private int numberOfPages;
    private String title;

    public PrintedFeatures(String title, int numberOfPages, List<String> authors) {
        noTitleEx(title);
        inNumPagesEx(numberOfPages);
        noPerfEx(authors);
        this.authors = authors;
        this.numberOfPages = numberOfPages;
        this.title = title;
    }

    @Override
    public List<String> getContributors() {
        List<String> result = new ArrayList<>();
        for (String item : authors) {
            result.add(item);
        }
        return result;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    private void noTitleEx(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Empty title");
        }
    }

    private void inNumPagesEx(int numberOfPages) {
        if (numberOfPages < 0) {
            throw new IllegalArgumentException("Invalid length");
        }
    }

    private void noPerfEx(List<String> authors) {
        if (authors == null || authors.size() < 1) {
            throw new IllegalArgumentException("No performer");
        }
    }

}