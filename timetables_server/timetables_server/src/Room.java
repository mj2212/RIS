public class Room extends AbstractEntity {
    private Lesson[] lessons = new Lesson[Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay()];

    public Lesson[] getLessons() {
        return this.lessons;
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }
}