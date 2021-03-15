package project3;

import java.util.ArrayList;
import java.util.List;

public class CatalogItem {
    private List<Feature> features = new ArrayList<>();
    private int price;
    private String registrationNumber;

    public CatalogItem(String registrationNumber, int price, Feature... features) {
        this.registrationNumber = registrationNumber;
        this.price = price;
        for (Feature item : features) {
            this.features.add(item);
        }
    }

    public List<String> getContributors() {
        List<String> result = new ArrayList<>();
        for (Feature item : features) {
            List<String> s = item.getContributors();
            for (String cont : s) {
                result.add(cont);
            }
        }
        return result;
    }

    public List<String> getTitles() {
        List<String> result = new ArrayList<>();
        for (Feature item : features) {
            result.add(item.getTitle());
        }
        return result;
    }

    public boolean hasAudioFeature() {
        boolean b = false;
        for (Feature item : features) {
            if (item instanceof AudioFeatures) {
                b = true;
            }
        }
        return b;
    }

    public boolean hasPrintedFeature() {
        boolean b = false;
        for (Feature item : features) {
            if (item instanceof PrintedFeatures) {
                b = true;
            }
        }
        return b;
    }

    public int numberOfPagesAtOneItem() {
        int result = 0;
        for (Feature item : features) {
            if (item instanceof PrintedFeatures) {
                result += ((PrintedFeatures) item).getNumberOfPages();
            }
        }
        return result;
    }

    public int fullLengthAtOneItem() {
        int result = 0;
        for (Feature item : features) {
            if (item instanceof AudioFeatures) {
                result += ((AudioFeatures) item).getLength();
            }
        }
        return result;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public int getPrice() {
        return price;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}