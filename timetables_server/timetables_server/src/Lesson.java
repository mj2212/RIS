import java.io.Serializable;

public class Lesson
        implements Serializable {
    public static final int ALL_WEEKS = 0;
    public static final int FIRST_WEEK = 1;
    public static final int SECOND_WEEK = 2;
    private int id;
    private Group group;
    private Room room;
    private Teacher teacher;
    private Subject subject;
    private SubGroup subGroup;
    private int cycle;
    private int count;
    private int duration;
    private LessonPosition position;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public SubGroup getSubGroup() {
        return this.subGroup;
    }

    public void setSubGroup(SubGroup subGroup) {
        this.subGroup = subGroup;
    }

    public int getCycle() {
        return this.cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LessonPosition getPosition() {
        return this.position;
    }

    public void setPosition(LessonPosition position) {
        this.position = position;
    }
}