import java.io.Serializable;

public class School
        implements Serializable {
    private int daysOfWeek;
    private int lessonsByDay;

    public School() {
        this.daysOfWeek = 5;
        this.lessonsByDay = 8;
    }

    public int getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(int daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getLessonsByDay() {
        return this.lessonsByDay;
    }

    public void setLessonsByDay(int lessonsByDay) {
        this.lessonsByDay = lessonsByDay;
    }
}