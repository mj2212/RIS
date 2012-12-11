import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class GeneralTimetableModel extends DefaultTableModel {
    public void add(ArrayList<Group> newGroups) {
        int first = Server.data.getGroups().size();
        int last = first + newGroups.size() - 1;
        Server.data.getGroups().addAll(newGroups);
        fireTableRowsInserted(first, last);
    }

    public void add(Group group) {
        int index = Server.data.getGroups().size();
        Server.data.getGroups().add(group);
        fireTableRowsInserted(index, index);
    }

    public void remove(Group group) {
        int index = Server.data.getGroups().size();
        Server.data.getGroups().remove(group);
        fireTableRowsDeleted(index, index);
    }

    public int getRowCount() {
        if (Server.data == null) return 0;
        return Server.data.getGroups().size();
    }

    public int getColumnCount() {
        if (Server.data == null) return 0;
        return (Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay());
    }

    public Group getGroup(int row) {
        if (Server.data.getGroups().size() > 0)
            return (Server.data.getGroups().get(row));

        return null;
    }

    public Class getColumnClass(int column) {
        try {
            return getValueAt(0, column).getClass();
        }
        catch (NullPointerException ignored) {
        }
        return new Object().getClass();
    }

    public Object getValueAt(int row, int column) {
        Group group = getGroup(row);
        return group.getLessons()[column];
    }

    public void setValueAt(Object value, int row, int column) {
        Group group = getGroup(row);
        Lesson lesson = (Lesson) value;
        group.getLessons()[column] = lesson;
        if (lesson != null) {
            if (lesson.getPosition() != null) {
                int previousColumn = lesson.getPosition().getColumn();
                lesson.getTeacher().getLessons()[previousColumn] = null;
                lesson.getRoom().getLessons()[previousColumn] = null;
            }
            lesson.setPosition(new LessonPosition(column));
            fireTableCellUpdated(row, column);
            lesson.getTeacher().getLessons()[column] = lesson;
            lesson.getRoom().getLessons()[column] = lesson;
            ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).fireTableDataChanged();
            ((RoomsTimetableModel) Server.mainWindow.roomsTable.getModel()).fireTableDataChanged();
        }
    }

    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }
}