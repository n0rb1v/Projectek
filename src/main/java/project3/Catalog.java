package project3;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    List<CatalogItem> catalogItems = new ArrayList<>();

    public void addItem(CatalogItem catalogItem) {
        catalogItems.add(catalogItem);
    }

    public double averagePageNumberOver(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Page number must be positive");
        }
        int result = 0;
        int sum = 0;
        for (CatalogItem item : catalogItems) {
            if (item.hasPrintedFeature()) {
                if (item.numberOfPagesAtOneItem() > number) {
                    result += item.numberOfPagesAtOneItem();
                    sum++;
                }
            }
        }
        if (sum == 0) {
            throw new IllegalArgumentException("No page");
        }
        return result / (double) sum;
    }

    public void deleteItemByRegistrationNumber(String s) {
        for (int i = 0; i < catalogItems.size(); i++) {
            if (s.equals(catalogItems.get(i).getRegistrationNumber())) {
                catalogItems.remove(i);
            }
        }
    }

    public int getAllPageNumber() {
        int result = 0;
        for (CatalogItem item : catalogItems) {
            if (item.hasPrintedFeature()) {
                result += item.numberOfPagesAtOneItem();
            }
        }
        return result;
    }

    public int getFullLength() {
        int result = 0;
        for (CatalogItem item : catalogItems) {
            if (item.hasAudioFeature()) {
                result += item.fullLengthAtOneItem();
            }
        }
        return result;
    }

    public List<CatalogItem> findByCriteria(SearchCriteria searchCriteria) {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem item : catalogItems) {
            if (searchCriteria.hasTitle() && searchCriteria.hasContributor()) {
                if ((item.getTitles().toString().contains(searchCriteria.getTitle())) && (item.getContributors().toString().contains(searchCriteria.getContributor()))) {
                    result.add(item);
                }
            }else {
                if (searchCriteria.hasTitle()) {
                    if (item.getTitles().toString().contains(searchCriteria.getTitle())) {
                        result.add(item);
                    }
                }
                if (searchCriteria.hasContributor()) {
                    if (item.getContributors().toString().contains(searchCriteria.getContributor())) {
                        result.add(item);
                    }
                }

            }
        }
        return result;
    }

    public List<CatalogItem> getAudioLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem item : catalogItems) {
            if (item.hasAudioFeature()) {
                result.add(item);
            }
        }
        return result;
    }

    public List<CatalogItem> getPrintedLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem item : catalogItems) {
            if (item.hasPrintedFeature()) {
                result.add(item);
            }
        }
        return result;
    }
}