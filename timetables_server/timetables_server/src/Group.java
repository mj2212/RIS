import java.util.ArrayList;


public class Group extends AbstractEntity {
    private ArrayList<ArrayList<SubGroup>> subGroups;
    private ArrayList<Student> students = new ArrayList<Student>();
    private Lesson[] lessons = new Lesson[Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay()];

    public Group() {
        this.subGroups = new ArrayList();
        ArrayList firstGroups = new ArrayList();
        firstGroups.add(new SubGroup("Весь клас"));
        this.subGroups.add(firstGroups);
        //for (Lesson l : this.lessons)
        //    l = null;
    }

    public ArrayList<ArrayList<SubGroup>> getSubGroups() {
        return this.subGroups;
    }

    public void setSubGroups(ArrayList<ArrayList<SubGroup>> subGroups) {
        this.subGroups = subGroups;
    }

    public Lesson[] getLessons() {
        return this.lessons;
    }

    public void setLessons(Lesson[] lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
}