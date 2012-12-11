import java.awt.*;

public class Teacher extends AbstractEntity {
    private Color color;
    private Lesson[] lessons = new Lesson[Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay()];

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Lesson[] getLessons() {
        return this.lessons;
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }
}