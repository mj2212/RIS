import java.io.Serializable;

public class LessonPosition
        implements Serializable {
    public static int MONDAY = 1;
    public static int TUESDAY = 2;
    public static int WEDNWSDAY = 3;
    public static int THURSDAY = 4;
    public static int FRIDAY = 5;
    public static int SATURDAY = 6;
    public static int SUNDAY = 7;
    private int dayOfWeek;
    private int number;

    public LessonPosition(int day, int lesson) {
        this.dayOfWeek = day;
        this.number = lesson;
    }

    public LessonPosition(int column) {
        this.dayOfWeek = (column / Server.data.getSchool().getLessonsByDay() + 1);
        this.number = (column % Server.data.getSchool().getLessonsByDay());
    }

    public int getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getColumn() {
        return (dayOfWeek - 1) * Server.data.getSchool().getLessonsByDay() + number;
    }
}