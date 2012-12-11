import java.io.Serializable;
import java.util.ArrayList;

public class Data
        implements Serializable {
    private String name;
    private School school;
    private ArrayList<Group> groups;
    private ArrayList<Room> rooms;
    private ArrayList<Lesson> lessons;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private ArrayList<Student> students;
//    private ArrayList<JournalRecord> journals;

    public Data() {
        this.school = new School();
        this.groups = new ArrayList();
        this.rooms = new ArrayList();
        this.lessons = new ArrayList();
        this.teachers = new ArrayList();
        this.subjects = new ArrayList();
        this.students = new ArrayList();
//        this.journals = new ArrayList();
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Lesson> getLessons() {
        return this.lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<Teacher> getTeachers() {
        return this.teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

//    public ArrayList<JournalRecord> getJournals() {
//        return journals;
//    }
//
//    public ArrayList<JournalRecord> getJournalsByDate(Group group, Lesson lesson, String date) {
//        ArrayList<JournalRecord> result = new ArrayList<JournalRecord>();
//
//        for(Student student : group.getStudents()) {
//            for(JournalRecord record : journals) {
//                if(record.getStudent() == student && record.getDate() == date) {
//                    result.add(record);
//                } else {
//                    result.add(new JournalRecord(lesson, student, date, true, ""));
//                }
//            }
//        }
//
//        return result;
//    }
//
//    public void setJournals(ArrayList<JournalRecord> journals) {
//        this.journals = journals;
//    }
}