import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TeachersTimetableModel extends DefaultTableModel {
    public void add(ArrayList<Teacher> newTeachers) {
        int first = Server.data.getTeachers().size();
        int last = first + newTeachers.size() - 1;
        Server.data.getTeachers().addAll(newTeachers);
        fireTableRowsInserted(first, last);
    }

    public void add(Teacher teacher) {
        int index = Server.data.getTeachers().size();
        Server.data.getTeachers().add(teacher);
        fireTableRowsInserted(index, index);
    }

    public void remove(Teacher teacher) {
        int index = Server.data.getTeachers().size();
        Server.data.getTeachers().remove(teacher);
        fireTableRowsDeleted(index, index);
    }

    public int getRowCount() {
        if (Server.data == null) return 0;
        return Server.data.getTeachers().size();
    }

    public int getColumnCount() {
        if (Server.data == null) return 0;
        return (Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay());
    }

    public Teacher getTeacher(int row) {
        if (Server.data.getTeachers().size() > 0)
            return (Server.data.getTeachers().get(row));

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
        Teacher teacher = getTeacher(row);
        return teacher.getLessons()[column];
    }

    public void setValueAt(Object value, int row, int column) {
        Teacher teacher = getTeacher(row);
        Lesson lesson = (Lesson) value;
        teacher.getLessons()[column] = lesson;
        if (lesson != null) {
            if (lesson.getPosition() != null) {
                int previousColumn = lesson.getPosition().getColumn();
                lesson.getGroup().getLessons()[previousColumn] = null;
                lesson.getRoom().getLessons()[previousColumn] = null;
            }
            lesson.setPosition(new LessonPosition(column));
            fireTableCellUpdated(row, column);
            lesson.getGroup().getLessons()[column] = lesson;
            lesson.getRoom().getLessons()[column] = lesson;
            ((GeneralTimetableModel) Server.mainWindow.generalTable.getModel()).fireTableDataChanged();
            ((RoomsTimetableModel) Server.mainWindow.roomsTable.getModel()).fireTableDataChanged();
        }
        //fireTableCellUpdated(row, column);
    }

    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }
}