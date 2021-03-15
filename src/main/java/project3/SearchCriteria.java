package project3;

public class SearchCriteria {
    public String contributor;
    public String title;

    public static SearchCriteria createByBoth(String s1, String s2) {
        if (s1 == null || s1.isBlank()) {
            throw new IllegalArgumentException("Title miss");
        }
        if (s2 == null || s2.isBlank()) {
            throw new IllegalArgumentException("Contributor miss");
        }
        return new SearchCriteria(s2, s1);
    }

    public static SearchCriteria createByContributor(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Contributor miss");
        }
        return new SearchCriteria(s, null);
    }

    public static SearchCriteria createByTitle(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("Title miss");
        }
        return new SearchCriteria(null, s);
    }

    private SearchCriteria(String contributor, String title) {
        this.contributor = contributor;
        this.title = title;
    }

    public boolean hasContributor() {
        if (contributor != null) {
            return true;
        }
        return false;
    }

    public boolean hasTitle() {
        if (title != null) {
            return true;
        }
        return false;
    }

    public String getContributor() {
        return contributor;
    }

    public String getTitle() {
        return title;
    }
}