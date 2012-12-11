import java.io.Serializable;

public abstract class AbstractEntity
        implements Serializable {
    private String name;
    private String shortName;
    private String countOfLessons;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountOfLessons() {
        return this.countOfLessons;
    }

    public void setCountOfLessons(String countOfLessons) {
        this.countOfLessons = countOfLessons;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}