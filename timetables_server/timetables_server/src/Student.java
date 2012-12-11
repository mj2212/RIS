import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: wayfinder
 * Date: 30 трав 2010
 * Time: 10:42:30
 * To change this template use File | Settings | File Templates.
 */
public class Student implements Serializable {
    private int id;
    private String name;
    private Group group;

    public Student() {
//        JournalRecord record = new JournalRecord(null, this, null, true, "");
//        Server.data.getJournals().add(record);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
