import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class RoomsTimetableColumnModel extends DefaultTableColumnModel {
    public static final ColumnGroup[] COLUMN_GROUPS = {new ColumnGroup("Понеділок"), new ColumnGroup("Вівторок"), new ColumnGroup("Середа"), new ColumnGroup("Четвер"), new ColumnGroup("Пятниця"), new ColumnGroup("Субота"), new ColumnGroup("Неділя")};
   
    public RoomsTimetableColumnModel(GroupableTableHeader header) {
        for (int i = 0; i < Server.data.getSchool().getDaysOfWeek(); i++) {
            for (int j = 0; j < Server.data.getSchool().getLessonsByDay(); j++) {
                TableColumn column = new TableColumn();
                column.setModelIndex(i * Server.data.getSchool().getLessonsByDay() + j);
                column.setHeaderValue(String.valueOf(j + 1));
                column.setPreferredWidth(32);
                column.setCellRenderer(new MainWindow.LessonsTableCellRenderer());

                addColumn(column);
                COLUMN_GROUPS[i].add(column);
            }
            header.addColumnGroup(COLUMN_GROUPS[i]);
        }
    }
}