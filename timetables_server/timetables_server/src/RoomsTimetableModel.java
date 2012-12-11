import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class RoomsTimetableModel extends DefaultTableModel {
    public void add(ArrayList<Room> newRooms) {
        int first = Server.data.getRooms().size();
        int last = first + newRooms.size() - 1;
        Server.data.getRooms().addAll(newRooms);
        fireTableRowsInserted(first, last);
    }

    public void add(Room room) {
        int index = Server.data.getRooms().size();
        Server.data.getRooms().add(room);
        fireTableRowsInserted(index, index);
    }

    public void remove(Room room) {
        int index = Server.data.getRooms().size();
        Server.data.getRooms().remove(room);
        fireTableRowsDeleted(index, index);
    }

    public int getRowCount() {
        if (Server.data == null) return 0;
        return Server.data.getRooms().size();
    }

    public int getColumnCount() {
        if (Server.data == null) return 0;
        return (Server.data.getSchool().getDaysOfWeek() * Server.data.getSchool().getLessonsByDay());
    }

    public Room getRoom(int row) {
        if (Server.data.getRooms().size() > 0)
            return (Server.data.getRooms().get(row));

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
        Room room = getRoom(row);
        return room.getLessons()[column];
    }

    public void setValueAt(Object value, int row, int column) {
        Room room = getRoom(row);
        Lesson lesson = (Lesson) value;
        room.getLessons()[column] = lesson;
        if (lesson != null) {
            if (lesson.getPosition() != null) {
                int previousColumn = lesson.getPosition().getColumn();
                lesson.getTeacher().getLessons()[previousColumn] = null;
                lesson.getGroup().getLessons()[previousColumn] = null;
            }
            lesson.setPosition(new LessonPosition(column));
            fireTableCellUpdated(row, column);
            lesson.getTeacher().getLessons()[column] = lesson;
            lesson.getGroup().getLessons()[column] = lesson;
            ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).fireTableDataChanged();
            ((GeneralTimetableModel) Server.mainWindow.generalTable.getModel()).fireTableDataChanged();
        }
        //fireTableCellUpdated(row, column);
    }

    public boolean isCellEditable(int rowIndex, int colIndex) {
        return false;
    }
}